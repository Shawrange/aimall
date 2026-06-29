package com.aimall.controller;

import com.aimall.component.EsSearchComponent;
import com.aimall.component.RedisComponent;
import com.aimall.constants.Constants;
import com.aimall.entity.dto.RagDataDTO;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.entity.enums.ProductStatusEnum;
import com.aimall.entity.enums.RagDataTypeEnum;
import com.aimall.entity.po.ProductInfo;
import com.aimall.entity.po.RagQuestion;
import com.aimall.entity.query.ProductInfoQuery;
import com.aimall.entity.query.RagQuestionQuery;
import com.aimall.entity.vo.ResponseVO;
import com.aimall.service.ProductInfoService;
import com.aimall.service.RagQuestionService;
import com.aimall.service.StatisticsInfoService;
import com.aimall.utils.DateUtil;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * 这个只是一个临时工具，实际开发无需这样操作，都是通过任务来处理，这里是方便同学们处理历史数据 作用如下
 * 1、全量同步统计数据
 * 2、全量同步商品信息到向量数据库
 * 3、全量同步rag知识库到向量数据库
 */

@RestController
@RequestMapping("/tool")
@Validated
public class ToolController extends ABaseController {

    @Resource
    private StatisticsInfoService statisticsInfoService;

    @Resource
    private ProductInfoService productInfoService;

    @Resource
    private RagQuestionService ragQuestionService;

    @Resource
    private RedisComponent redisComponent;

    @Resource
    private EsSearchComponent esSearchComponent;


    @RequestMapping("/statistics")
    public ResponseVO statistics() {
        String beforeDate = DateUtil.getBeforeDay(7, DateTimePatternEnum.YYYY_MM_DD.getPattern());
        List<String> dateList = DateUtil.getDateRange(beforeDate, DateUtil.format(new Date(), DateTimePatternEnum.YYYY_MM_DD.getPattern()),
                DateTimePatternEnum.YYYY_MM_DD.getPattern());
        for (String date : dateList) {
            statisticsInfoService.statisticsData(date);
        }
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/productData")
    public ResponseVO productData() {
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setStatus(ProductStatusEnum.ON_SALE.getStatus());
        List<ProductInfo> productInfoList = productInfoService.findListByParam(productInfoQuery);
        for (ProductInfo productInfo : productInfoList) {
            esSearchComponent.saveProduct(productInfo.getProductId());
            redisComponent.sendMessage(Constants.REDIS_QUEUE_RAG_DATA, new RagDataDTO(productInfo.getProductId(), RagDataTypeEnum.PRODUCT.getType()));
        }
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/ragData")
    public ResponseVO ragData() {
        RagQuestionQuery ragQuestionQuery = new RagQuestionQuery();
        List<RagQuestion> ragQuestionList = ragQuestionService.findListByParam(ragQuestionQuery);
        for (RagQuestion ragQuestion : ragQuestionList) {
            redisComponent.sendMessage(Constants.REDIS_QUEUE_RAG_DATA, new RagDataDTO(ragQuestion.getQuestionId().toString(), RagDataTypeEnum.FAQ.getType()));
        }
        return getSuccessResponseVO(null);
    }
}
