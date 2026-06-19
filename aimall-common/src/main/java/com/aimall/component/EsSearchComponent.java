package com.aimall.component;

import com.aimall.entity.dto.ProductInfoDTO;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.enums.ProductStatusEnum;
import com.aimall.entity.enums.SearchFieldTypeEnum;
import com.aimall.entity.enums.SearchSortTypeEnum;
import com.aimall.entity.po.ProductInfo;
import com.aimall.entity.query.ProductInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.mappers.ProductInfoMapper;
import com.aimall.utils.CopyTools;
import com.aimall.utils.JsonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EsSearchComponent {
    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    @Resource
    private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;

    @PostConstruct
    public void createIndexWithIK() {
        try {
            IndexOperations indexOps = elasticsearchOperations.indexOps(ProductInfoDTO.class);
            if (indexOps.exists()) {
                return;
            }
            String json = """
                    {
                      "analysis": {
                        "analyzer": {
                          "ik_max_word": {
                            "type": "custom",
                            "tokenizer": "ik_max_word"
                          },
                          "ik_smart": {
                            "type": "custom",
                            "tokenizer": "ik_smart"
                          }
                        }
                      }
                    }
                    """;
            // 鍒涘缓绱㈠紩骞跺簲鐢ㄨ缃?
            indexOps.create(JsonUtils.convertJson2Obj(json, Map.class));
            // 鍒涘缓鏄犲皠锛堜細搴旂敤 @Field 娉ㄨВ锛?
            Document mapping = indexOps.createMapping(ProductInfoDTO.class);
            indexOps.putMapping(mapping);
            log.info("绱㈠紩鍒涘缓鎴愬姛锛屽凡搴旂敤IK鍒嗚瘝鍣?);
        } catch (Exception e) {
            log.error("鍒涘缓绱㈠紩澶辫触", e);
            throw new RuntimeException("鍒涘缓绱㈠紩澶辫触", e);
        }
    }

    public PaginationResultVO<ProductInfoDTO> searchProducts(String keyWords, BigDecimal priceFrom, BigDecimal priceTo, String sortType, String sortField,
                                                             Integer pageNo) {
        try {
            // 鍙傛暟澶勭悊
            pageNo = pageNo == null ? 1 : pageNo;
            //es 鍒嗛〉浠?寮€濮?
            pageNo = pageNo - 1;
            int pageSize = PageSize.SIZE15.getSize();
            // 1. 鏋勫缓鏌ヨ鏉′欢
            Criteria criteria = new Criteria();


            // 閽堝鐭瘝浼樺寲锛氬鏋滄悳绱㈣瘝寰堢煭锛?=2涓瓧锛夛紝浣跨敤鏇村鏉剧殑鍖归厤
            if (keyWords.length() <= 2) {
                // 浣跨敤bool鏌ヨ锛屾彁楂樺彫鍥炵巼
                Criteria nameCriteria = new Criteria();

                // 鏂瑰紡1锛氭櫘閫氬垎璇嶅尮閰?
                nameCriteria = nameCriteria.or(new Criteria("productName").contains(keyWords));

                // 鏂瑰紡2锛氶€氶厤绗﹀尮閰?
                nameCriteria = nameCriteria.or(new Criteria("productName").expression("*" + keyWords + "*"));

                // 鏂瑰紡3锛氱煭璇尮閰嶏紙閫傚悎鐭瘝锛?
                nameCriteria = nameCriteria.or(new Criteria("productName").matches(keyWords));

                criteria = criteria.and(nameCriteria);
            } else {
                // 闀胯瘝浣跨敤鏅€氬垎璇嶅尮閰?
                criteria = criteria.and("productName").contains(keyWords);
            }

            // 浠锋牸杩囨护
            if (priceFrom != null || priceTo != null) {
                Criteria priceCriteria = new Criteria();
                priceCriteria = priceCriteria.and("minPrice");
                if (priceFrom != null) {
                    priceCriteria = priceCriteria.greaterThanEqual(priceFrom);
                }
                if (priceTo != null) {
                    priceCriteria = priceCriteria.lessThanEqual(priceTo);
                }
                criteria = criteria.and(priceCriteria);
            }

            SearchSortTypeEnum sortTypeEnum = SearchSortTypeEnum.getByType(sortType);
            sortTypeEnum = sortTypeEnum == null ? SearchSortTypeEnum.DESC : sortTypeEnum;

            SearchFieldTypeEnum fieldTypeEnum = SearchFieldTypeEnum.getByFieldType(sortField);
            fieldTypeEnum = fieldTypeEnum == null ? SearchFieldTypeEnum.COMPOSITE : fieldTypeEnum;

            // 2. 鍒嗛〉鍜屾帓搴?
            Sort sort = Sort.by(sortTypeEnum.getDirection(), fieldTypeEnum.getField());
            Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
            // 3. 鎵ц鏌ヨ
            CriteriaQuery query = new CriteriaQuery(criteria);
            query.setPageable(pageable);
            SearchHits<ProductInfoDTO> searchHits = elasticsearchOperations.search(query, ProductInfoDTO.class);
            // 4. 澶勭悊缁撴灉
            List<ProductInfoDTO> products = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
            long totalHits = searchHits.getTotalHits();
            int totalPages = (int) Math.ceil((double) totalHits / pageSize);
            return new PaginationResultVO((int) totalHits, pageSize, pageNo + 1, totalPages, products);
        } catch (Exception e) {
            log.error("鎼滅储澶辫触", e);
            return new PaginationResultVO<>(0, PageSize.SIZE15.getSize(), pageNo != null ? pageNo : 0, 0, new ArrayList<>());
        }
    }

    public void saveProduct(String productId) {
        ProductInfo product = productInfoMapper.selectByProductId(productId);
        ProductInfoDTO productInfoDTO = CopyTools.copy(product, ProductInfoDTO.class);
        if (ProductStatusEnum.ON_SALE.getStatus().equals(product.getStatus())) {
            elasticsearchOperations.save(productInfoDTO);
        } else {
            elasticsearchOperations.delete(productId, ProductInfoDTO.class);
        }
    }
}

