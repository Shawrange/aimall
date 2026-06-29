package com.aimall.evaluation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("rag-eval")
@EnabledIfSystemProperty(named = "rag.eval.enabled", matches = "true")
class RagRecallEvaluationTest {

    private static final String DEFAULT_DATASET = "rag-eval-products.json";
    private static final String PRODUCT_FILTER = "dataType == 'product'";

    @Test
    void evaluateProductRecallWithRealEmbeddingAndVectorStore() throws Exception {
        assertRequiredProperty("spring.ai.openai.api-key");
        assertRequiredProperty("spring.elasticsearch.uris");

        EvaluationDataset dataset = loadDataset(systemProperty("rag.eval.dataset", DEFAULT_DATASET));
        int productCount = Integer.parseInt(systemProperty("rag.eval.product-count", "10000"));
        int caseCount = Integer.parseInt(systemProperty("rag.eval.case-count", "100"));
        int batchSize = Integer.parseInt(systemProperty("rag.eval.embedding-batch-size", "10"));
        boolean reseedProducts = Boolean.parseBoolean(systemProperty("rag.eval.reseed-products", "true"));
        dataset = dataset.withGeneratedProducts(productCount);
        dataset = dataset.withGeneratedCases(caseCount);
        int[] topKs = parseTopKs(systemProperty("rag.eval.top-k-list", "5,10,30"));
        double minRecallAt10 = Double.parseDouble(systemProperty("rag.eval.min-recall-at-10", "0.80"));
        double minRecallAt30 = Double.parseDouble(systemProperty("rag.eval.min-recall-at-30", "0.90"));

        SpringApplication application = new SpringApplication(EvaluationApplication.class);
        application.setDefaultProperties(Map.of(
                "spring.config.name", "rag-eval",
                "spring.main.web-application-type", "none",
                "spring.autoconfigure.exclude", String.join(",",
                        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
                        "org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration",
                        "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration",
                        "org.springframework.ai.model.ollama.autoconfigure.OllamaEmbeddingAutoConfiguration"
                )
        ));

        try (ConfigurableApplicationContext context = application.run()) {
            VectorStore vectorStore = context.getBean(VectorStore.class);
            if (reseedProducts) {
                seedProducts(vectorStore, dataset.products(), batchSize);
            }

            EvaluationReport report = evaluate(vectorStore, dataset.cases(), topKs);
            printReport(report);

            assertThat(report.averageRecallAt(10))
                    .as("average recall@10")
                    .isGreaterThanOrEqualTo(minRecallAt10);
            assertThat(report.averageRecallAt(30))
                    .as("average recall@30")
                    .isGreaterThanOrEqualTo(minRecallAt30);
        }
    }

    private EvaluationDataset loadDataset(String datasetPath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(datasetPath);
        assertThat(resource.exists())
                .as("dataset must exist on test classpath: " + datasetPath)
                .isTrue();
        EvaluationDataset dataset = mapper.readValue(resource.getInputStream(), EvaluationDataset.class);
        assertThat(dataset.products()).as("products").isNotEmpty();
        assertThat(dataset.cases()).as("cases").isNotEmpty();
        Set<String> productIds = dataset.products().stream()
                .map(EvaluationProduct::productId)
                .collect(Collectors.toSet());
        for (EvaluationCase evaluationCase : dataset.cases()) {
            assertThat(productIds)
                    .as("all expected products must exist for query: " + evaluationCase.query())
                    .containsAll(evaluationCase.expectedProductIds());
        }
        return dataset;
    }

    private void seedProducts(VectorStore vectorStore, List<EvaluationProduct> products, int batchSize) {
        assertThat(batchSize)
                .as("embedding batch size")
                .isBetween(1, 10);
        List<String> documentIds = products.stream().map(EvaluationProduct::productId).toList();
        vectorStore.delete(documentIds);

        List<Document> documents = products.stream()
                .map(product -> new Document(product.productId(), product.content(), productMetadata(product)))
                .toList();
        for (int from = 0; from < documents.size(); from += batchSize) {
            int to = Math.min(from + batchSize, documents.size());
            vectorStore.add(documents.subList(from, to));
        }
    }

    private Map<String, Object> productMetadata(EvaluationProduct product) {
        Map<String, Object> metadata = new LinkedHashMap<>();
        metadata.put("dataType", "product");
        metadata.put("productId", product.productId());
        metadata.put("productName", product.productName());
        metadata.put("source", "rag-recall-evaluation");
        return metadata;
    }

    private EvaluationReport evaluate(VectorStore vectorStore, List<EvaluationCase> cases, int[] topKs) {
        List<CaseResult> results = new ArrayList<>();
        int maxTopK = Arrays.stream(topKs).max().orElse(30);
        for (EvaluationCase evaluationCase : cases) {
            SearchRequest request = SearchRequest.builder()
                    .query(evaluationCase.query())
                    .filterExpression(PRODUCT_FILTER)
                    .topK(maxTopK)
                    .build();
            List<Document> documents = vectorStore.similaritySearch(request);
            List<String> productIds = documents.stream()
                    .map(document -> Objects.toString(document.getMetadata().get("productId"), null))
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();
            Map<Integer, Double> recalls = new LinkedHashMap<>();
            for (int topK : topKs) {
                recalls.put(topK, recallAt(productIds, evaluationCase.expectedProductIds(), topK));
            }
            results.add(new CaseResult(evaluationCase.query(), evaluationCase.expectedProductIds(), productIds, recalls));
        }
        return new EvaluationReport(results);
    }

    private double recallAt(List<String> actualProductIds, List<String> expectedProductIds, int topK) {
        Set<String> expected = new LinkedHashSet<>(expectedProductIds);
        Set<String> actual = actualProductIds.stream().limit(topK).collect(Collectors.toCollection(LinkedHashSet::new));
        long hitCount = expected.stream().filter(actual::contains).count();
        return expected.isEmpty() ? 0 : (double) hitCount / expected.size();
    }

    private int[] parseTopKs(String value) {
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();
    }

    private void printReport(EvaluationReport report) {
        System.out.println();
        System.out.println("RAG product recall evaluation");
        System.out.println("----------------------------------------");
        System.out.println("case count = " + report.results().size());
        System.out.println();
        for (CaseResult result : report.results()) {
            System.out.println("query: " + result.query());
            System.out.println("expected: " + result.expectedProductIds());
            System.out.println("actual top ids: " + result.actualProductIds().stream().limit(10).toList());
            System.out.println("recall: " + result.recalls());
            System.out.println();
        }
        System.out.println("average recall@5  = " + format(report.averageRecallAt(5)));
        System.out.println("average recall@10 = " + format(report.averageRecallAt(10)));
        System.out.println("average recall@30 = " + format(report.averageRecallAt(30)));
    }

    private String format(double value) {
        return BigDecimal.valueOf(value).setScale(4, RoundingMode.HALF_UP).toPlainString();
    }

    private void assertRequiredProperty(String key) {
        String value = systemProperty(key, "");
        assertThat(value)
                .as("required property " + key)
                .isNotBlank()
                .doesNotContain("111111111111");
    }

    private String systemProperty(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = System.getenv(key.toUpperCase().replace('.', '_').replace('-', '_'));
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }

    record EvaluationDataset(List<EvaluationProduct> products, List<EvaluationCase> cases) {
        EvaluationDataset withGeneratedProducts(int targetCount) {
            if (products.size() >= targetCount) {
                return this;
            }
            List<EvaluationProduct> generatedProducts = new ArrayList<>(products);
            int index = 1;
            while (generatedProducts.size() < targetCount) {
                generatedProducts.add(generatedProduct(index));
                index++;
            }
            return new EvaluationDataset(generatedProducts, cases);
        }

        EvaluationDataset withGeneratedCases(int targetCount) {
            if (cases.size() >= targetCount) {
                return this;
            }
            List<EvaluationCase> generatedCases = new ArrayList<>(cases);
            int index = 0;
            while (generatedCases.size() < targetCount) {
                EvaluationCase evaluationCase = generatedCase(index);
                boolean exists = generatedCases.stream().anyMatch(item -> item.query().equals(evaluationCase.query()));
                if (!exists) {
                    generatedCases.add(evaluationCase);
                }
                index++;
            }
            return new EvaluationDataset(products, generatedCases);
        }

        private EvaluationProduct generatedProduct(int index) {
            String[] scenes = {
                    "学生平价蓝牙耳机，网课通勤，轻便入耳，长续航",
                    "主动降噪耳机，地铁飞机通勤，透明模式，高清麦克风",
                    "老人手机，大字体，大音量，一键求助，超长待机",
                    "高性能游戏手机，手游直播，散热系统，高刷新率屏幕",
                    "男士跑鞋，缓震回弹，健身训练，防滑透气",
                    "女士健步鞋，旅行散步，软底舒适，长走不累",
                    "轻薄办公笔记本，会议文档，学习网课，长续航",
                    "电竞游戏本，独立显卡，视频剪辑，大型游戏",
                    "商务双肩包，电脑隔层，防泼水，通勤出差",
                    "机械键盘，程序员打字，游戏办公，背光轴体",
                    "人体工学鼠标，长时间办公，护腕静音，无线连接",
                    "运动智能手表，心率睡眠，跑步骑行，防水长续航",
                    "厨房收纳用品，大容量，易清洗，适合家庭整理",
                    "家用清洁工具，静音耐用，适合日常卫生打扫",
                    "户外露营装备，便携防水，适合短途旅行"
            };
            String[] modifiers = {
                    "入门", "升级", "青春", "专业", "轻享",
                    "标准", "增强", "舒适", "旗舰", "经典"
            };
            String scene = scenes[index % scenes.length];
            String modifier = modifiers[index % modifiers.length];
            String productId = "eval-generated-product-" + String.format("%05d", index);
            String productName = "评测干扰商品 " + index + " " + modifier + "款";
            String content = "商品名称：" + productName + "。核心卖点：" + scene
                    + "。补充描述：这是相似场景干扰商品，可能与标准答案共享品类、用途或用户表达，用于检验真实召回排序稳定性。样本编号：" + index + "。";
            return new EvaluationProduct(productId, productName, content);
        }

        private EvaluationCase generatedCase(int index) {
            List<CaseProfile> profiles = caseProfiles();
            CaseProfile profile = profiles.get(index % profiles.size());
            String query = profile.queries().get((index / profiles.size()) % profile.queries().size());
            return new EvaluationCase(query, List.of(profile.productId()));
        }

        private List<CaseProfile> caseProfiles() {
            return List.of(
                    new CaseProfile("eval-product-earbuds-student", List.of(
                            "预算不多，想给学生党买个蓝牙耳机，听课和坐公交都能用",
                            "有没有适合大学生的耳机，轻一点，续航别太差，价格也别太夸张",
                            "我弟上网课老用有线耳机，想换个便宜点的无线耳机",
                            "找个学生平时通勤听歌的耳机，最好通话也清楚点",
                            "想买个白色入耳式耳机，学生用，不追求特别贵的",
                            "给孩子上网课用的蓝牙耳机，戴久点别太难受",
                            "有没有性价比高一点的耳机，适合学生日常听课",
                            "想找个轻便的无线耳机，学生宿舍、路上都能用",
                            "耳机别太贵，主要听网课和音乐，学生用够了就行",
                            "帮我挑个续航久一点的学生蓝牙耳机"
                    )),
                    new CaseProfile("eval-product-earbuds-noise-cancel", List.of(
                            "通勤路上太吵了，想买个降噪明显一点的耳机",
                            "有没有适合地铁上用的降噪耳机，最好无线的",
                            "经常坐飞机，想找个能安静一点的耳机",
                            "办公室有点吵，想买个主动降噪耳机",
                            "我想要那种能切透明模式的降噪耳机",
                            "找个黑色真无线耳机，重点是降噪和麦克风",
                            "每天挤地铁，耳机最好能隔掉环境声",
                            "想换个通勤耳机，降噪要比普通耳机强",
                            "有没有能降低风噪人声的蓝牙耳机",
                            "帮我推荐一款适合嘈杂环境的无线降噪耳机"
                    )),
                    new CaseProfile("eval-product-phone-elder", List.of(
                            "老人用的手机有没有，字大声音大就行",
                            "给奶奶买手机，按键清楚一点，最好能一键求助",
                            "我爸不会用智能机，想要简单大字的手机",
                            "有没有待机时间长一点的老人机",
                            "家里老人听力不好，想买个音量大的手机",
                            "老人手机要实体按键，操作简单一点",
                            "想给长辈买个红色老人机，能打电话就行",
                            "找个适合老人紧急联系的手机",
                            "给眼神不好的老人买手机，屏幕字要大",
                            "有没有 SOS 功能的老年人手机"
                    )),
                    new CaseProfile("eval-product-phone-gaming", List.of(
                            "玩游戏手机发热太严重，想换个散热好的",
                            "手游党想买手机，性能强一点，屏幕刷新率高一点",
                            "有没有适合打王者吃鸡的手机",
                            "我想找个不卡顿的游戏手机，内存大一点",
                            "直播和打游戏都用手机，配置要高些",
                            "手机主要玩大型手游，散热和芯片要好",
                            "想买黑色高性能手机，游戏体验好一点",
                            "帮我找个 12G 内存左右的游戏手机",
                            "最近手游帧率不稳，想换台强一点的手机",
                            "有没有适合长时间玩游戏不烫的手机"
                    )),
                    new CaseProfile("eval-product-shoes-men-running", List.of(
                            "男生跑步穿什么鞋舒服，最好有缓震",
                            "给男朋友买跑鞋，健身房和户外都能穿",
                            "有没有防滑一点的男款运动鞋",
                            "想找双男士训练鞋，透气轻一点",
                            "跑步膝盖容易累，鞋子想要回弹好点",
                            "男款黑白配色跑鞋有没有推荐",
                            "买双健身跑步都能用的男鞋",
                            "男生日常慢跑，脚感软一点的鞋",
                            "想找轻量透气的缓震跑鞋",
                            "帮我挑双男士运动训练鞋"
                    )),
                    new CaseProfile("eval-product-shoes-women-walking", List.of(
                            "女生出去旅游穿什么鞋走路不累",
                            "想买双女士休闲鞋，通勤散步都舒服",
                            "有没有软底的女鞋，走一天也还行",
                            "旅行要走很多路，想找轻便一点的鞋",
                            "米白色女士健步鞋有没有",
                            "给妈妈买双散步鞋，脚感舒服点",
                            "女生上班通勤想穿不累脚的鞋",
                            "找双适合日常走路的女士休闲鞋",
                            "不想穿高跟鞋，想要舒适好走的女鞋",
                            "帮我推荐一双旅行用的女士软底鞋"
                    )),
                    new CaseProfile("eval-product-laptop-office", List.of(
                            "办公用电脑，主要文档会议，想要轻薄一点",
                            "有没有适合上班族的轻便笔记本",
                            "我只写材料开视频会，电脑续航要好",
                            "学习和办公用的 14 寸笔记本推荐一下",
                            "想买台不重的电脑，平时带去公司",
                            "办公本别太笨重，内存和硬盘够用就行",
                            "经常出差，想要轻薄长续航笔记本",
                            "写论文开网课用什么笔记本合适",
                            "找个适合日常办公的轻薄电脑",
                            "不打游戏，主要处理文档和会议，买什么笔记本"
                    )),
                    new CaseProfile("eval-product-laptop-gaming", List.of(
                            "想买游戏本，能玩大型游戏也能剪片子",
                            "笔记本要独显，偶尔做视频剪辑",
                            "有没有配置强一点的 16 寸电脑，游戏用",
                            "晚上玩游戏，白天还要做点设计，电脑别太弱",
                            "想换台带 RTX 显卡的笔记本",
                            "游戏本屏幕刷新率高一点的有吗",
                            "剪视频卡得难受，想买性能笔记本",
                            "既要玩 3A 又要做 3D 建模，推荐电脑",
                            "找个电竞本，键盘最好有灯",
                            "大型游戏和生产力都能扛的笔记本"
                    )),
                    new CaseProfile("eval-product-bag-commute", List.of(
                            "每天背笔记本上班，想买个防水双肩包",
                            "有没有商务一点的电脑包，容量大些",
                            "通勤包要能放电脑和文件，下雨也不怕",
                            "上班族背的黑色双肩包推荐一下",
                            "想找个有电脑隔层的背包",
                            "出差能装笔记本的商务包有没有",
                            "双肩包别太花，简约能装电脑就行",
                            "办公室通勤用包，防泼水很重要",
                            "每天地铁背电脑，包要舒服耐用",
                            "帮我挑一个商务通勤电脑双肩包"
                    )),
                    new CaseProfile("eval-product-keyboard-mechanical", List.of(
                            "写代码多，想买把打字舒服的机械键盘",
                            "程序员用什么键盘合适，敲起来有手感",
                            "有没有 87 键的机械键盘，桌面别占太大",
                            "想买个带背光的键盘，办公游戏都能用",
                            "青轴键盘有没有推荐",
                            "每天敲字很多，键盘手感要好一点",
                            "桌面办公想换机械键盘",
                            "代码写久了想要个反馈清楚的键盘",
                            "白色背光机械键盘有吗",
                            "帮我找个适合打字和游戏的键盘"
                    ))
            );
        }
    }

    record EvaluationProduct(String productId, String productName, String content) {
    }

    record EvaluationCase(String query, List<String> expectedProductIds) {
    }

    record CaseProfile(String productId, List<String> queries) {
    }

    record CaseResult(String query, List<String> expectedProductIds, List<String> actualProductIds, Map<Integer, Double> recalls) {
    }

    record EvaluationReport(List<CaseResult> results) {
        double averageRecallAt(int topK) {
            return results.stream()
                    .map(CaseResult::recalls)
                    .map(recalls -> recalls.getOrDefault(topK, 0D))
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0D);
        }
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class EvaluationApplication {
    }
}
