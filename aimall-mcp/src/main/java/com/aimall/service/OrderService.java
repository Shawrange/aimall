package com.aimall.service;

import com.aimall.entity.enums.OrderStatusEnum;
import com.aimall.entity.enums.ResponseCodeEnum;
import com.aimall.entity.po.OrderLogisticsInfo;
import com.aimall.exception.BusinessException;
import com.aimall.service.OrderCommentService;
import com.aimall.service.OrderInfoService;
import com.aimall.service.OrderLogisticsInfoService;
import com.aimall.utils.JsonUtils;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private OrderLogisticsInfoService orderLogisticsInfoService;

    @Resource
    private OrderCommentService orderCommentService;

    @Tool(name = "refund", description = "閫€娆?)
    public String refund(@ToolParam(description = "鐢ㄦ埛ID") String userId, @ToolParam(description = "璁㈠崟ID") String orderId) {
        if (StringTools.isEmpty(orderId)) {
            return "璇锋彁渚涘畬鏁寸殑璁㈠崟鍙?;
        }
        try {
            orderInfoService.refundByOrderId(userId, orderId);
        } catch (BusinessException e) {
            return e.getMessage();
        } catch (Exception e) {
            return ResponseCodeEnum.CODE_500.getMsg();
        }
        return "閫€娆炬垚鍔?;
    }

    @Tool(name = "orderConfirm", description = "纭鏀惰揣")
    public String orderConfirm(@ToolParam(description = "鐢ㄦ埛ID") String userId,
                               @ToolParam(description = "璁㈠崟ID") String orderId) {
        try {
            orderInfoService.confirmOrder(userId, orderId);
            return "纭鏀惰揣鎴愬姛";
        } catch (BusinessException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "绯荤粺寮傚父";
        }
    }

    @Tool(name = "cancelOrder", description = "鍙栨秷璁㈠崟")
    public String cancelOrder(@ToolParam(description = "鐢ㄦ埛ID") String userId,
                              @ToolParam(description = "璁㈠崟ID") String orderId) {
        try {
            orderInfoService.cancelOrder(userId, orderId, OrderStatusEnum.CANCELLED);
            return "璁㈠崟鍙栨秷鎴愬姛";
        } catch (BusinessException e) {
            return e.getMessage();
        } catch (Exception e) {
            log.error("鍙栨秷璁㈠崟澶辫触", e);
            return "绯荤粺寮傚父";
        }
    }

    @Tool(name = "queryOrderLogistics", description = "鏌ヨ璁㈠崟鐗╂祦淇℃伅")
    public String queryOrderLogistics(@ToolParam(description = "鐢ㄦ埛ID") String userId, @ToolParam(description = "璁㈠崟ID") String orderId) {
        try {
            if (StringTools.isEmpty(userId) || StringTools.isEmpty(orderId)) {
                return "璇锋彁渚涜缁嗙殑璁㈠崟ID";
            }
            OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoService.getOrderLogisticsRecords(userId, orderId);
            return JsonUtils.convertObj2Json(orderLogisticsInfo);
        } catch (BusinessException e) {
            log.error("鏌ヨ鐗╂祦澶辫触", e);
            return e.getMessage();
        } catch (Exception e) {
            log.error("鏌ヨ鐗╂祦澶辫触", e);
            return "鏌ヨ鐗╂祦澶辫触";
        }
    }

    @Tool(name = "productReview", description = "鍟嗗搧璇勪环")
    public String productReview(@ToolParam(description = "鐢ㄦ埛ID") String userId,
                                @ToolParam(description = "璁㈠崟ID") String orderId,
                                @ToolParam(description = "璇勪环鍐呭") String commentContent,
                                @ToolParam(description = "璇勪环鏄熺骇1-5绾?) Integer star) {
        try {
            orderCommentService.postComment(userId, orderId, commentContent, null, star);
            return "璇勪环鎴愬姛";
        } catch (BusinessException e) {
            return e.getMessage();
        } catch (Exception e) {
            log.error("璇勪环澶辫触", e);
            return "绯荤粺寮傚父";
        }
    }
}

