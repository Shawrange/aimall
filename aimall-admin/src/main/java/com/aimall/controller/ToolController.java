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
 * 杩欎釜鍙槸涓€涓复鏃跺伐鍏凤紝瀹為檯寮€鍙戞棤闇€杩欐牱鎿嶄綔锛岄兘鏄€氳繃浠诲姟鏉ュ鐞嗭紝杩欓噷鏄柟渚垮悓瀛︿滑澶勭悊鍘嗗彶鏁版嵁 浣滅敤濡備笅
 * 1銆佸叏閲忓悓姝ョ粺璁℃暟鎹?
 * 2銆佸叏閲忓悓姝ュ晢鍝佷俊鎭埌鍚戦噺鏁版嵁搴?
 * 3銆佸叏閲忓悓姝ag鐭ヨ瘑搴撳埌鍚戦噺鏁版嵁搴?
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

