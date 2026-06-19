package com.aimall.task;

import com.aimall.component.RedisComponent;
import com.aimall.component.SpringContext;
import com.aimall.constants.Constants;
import com.aimall.entity.config.AppConfig;
import com.aimall.entity.dto.PayOrderNotifyDTO;
import com.aimall.entity.enums.ExecutorServiceSingletonEnum;
import com.aimall.entity.enums.OrderStatusEnum;
import com.aimall.entity.enums.PayChannelEnum;
import com.aimall.entity.po.OrderInfo;
import com.aimall.entity.po.OrderLogisticsInfo;
import com.aimall.entity.query.OrderInfoQuery;
import com.aimall.service.OrderInfoService;
import com.aimall.service.OrderLogisticsInfoService;
import com.aimall.service.PayChannel;
import com.aimall.utils.StringTools;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class PayOrderTask {


    @Resource
    private AppConfig appConfig;

    @Resource
    private RedisComponent redisComponent;

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private OrderLogisticsInfoService orderLogisticsInfoService;

    /**
     * 瀹為檯椤圭洰浼氫娇鐢?鍥炶皟閫氱煡锛屼笉浼氶噰鐢ㄨ疆璁牎楠屾敮浠樼姸鎬侊紝鍥炶皟閫氱煡蹇呴』灏嗘湇鍔″彂甯冿紝鍚﹀垯鏃犳硶鏀跺埌鍥炶皟閫氱煡
     */
    @PostConstruct
    public void checkPayOrder() {
        if (!appConfig.getAutoCheckpay()) {
            return;
        }
        ExecutorServiceSingletonEnum.INSTANCE.getExecutorService().execute(() -> {
            while (true) {
                try {
                    OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
                    orderInfoQuery.setOrderStatus(OrderStatusEnum.WAIT_PAYMENT.getStatus());
                    List<OrderInfo> orderInfoList = orderInfoService.findListByParam(orderInfoQuery);
                    for (OrderInfo orderInfo : orderInfoList) {
                        PayChannelEnum payChannelEnum = PayChannelEnum.getByPayScene(orderInfo.getPayScene());
                        PayChannel payChannel = (PayChannel) SpringContext.getBean(payChannelEnum.getBeanName());
                        PayOrderNotifyDTO payOrderNotifyDTO = payChannel.queryOrder(orderInfo.getPayOrderId());
                        if (payOrderNotifyDTO == null) {
                            continue;
                        }
                        orderInfoService.payOrderSuccess(payOrderNotifyDTO);
                    }
                    Thread.sleep(10000);
                } catch (Exception e) {
                    log.error("鏌ヨ鏀粯璁㈠崟澶辫触", e);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    /**
     * 瓒呮椂璁㈠崟
     */
    @PostConstruct
    public void consumeDelayOrder() {
        ExecutorServiceSingletonEnum.INSTANCE.getExecutorService().execute(() -> {
            while (true) {
                try {
                    Set<String> queueOrderList = redisComponent.getTimeOutOrder(Constants.REDIS_KEY_ORDER_DELAY_QUEUE);
                    if (queueOrderList == null || queueOrderList.isEmpty()) {
                        Thread.sleep(5000);
                        continue;
                    }
                    for (String orderId : queueOrderList) {
                        if (redisComponent.removeTimeOutOrder(Constants.REDIS_KEY_ORDER_DELAY_QUEUE, orderId) > 0) {
                            OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderId(orderId);
                            if (!OrderStatusEnum.WAIT_PAYMENT.getStatus().equals(orderInfo.getOrderStatus())) {
                                continue;
                            }
                            orderInfoService.cancelOrder(null, orderId, OrderStatusEnum.CLOSED);
                        }
                    }
                } catch (Exception e) {
                    log.error("澶勭悊瓒呮椂璁㈠崟澶辫触", e);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    /**
     * 绯荤粺鑷姩鍙戣揣
     */
    @PostConstruct
    public void consumeDeliveryOrder() {
        ExecutorServiceSingletonEnum.INSTANCE.getExecutorService().execute(() -> {
            while (true) {
                try {
                    Set<String> queuePayOrderList = redisComponent.getTimeOutOrder(Constants.REDIS_KEY_ORDER_DELAY_QUEUE_DELIVERY);
                    if (queuePayOrderList == null || queuePayOrderList.isEmpty()) {
                        Thread.sleep(5000);
                        continue;
                    }
                    for (String payOrderId : queuePayOrderList) {
                        if (redisComponent.removeTimeOutOrder(Constants.REDIS_KEY_ORDER_DELAY_QUEUE_DELIVERY, payOrderId) > 0) {
                            OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
                            orderInfoQuery.setPayOrderId(payOrderId);
                            List<OrderInfo> orderInfoList = orderInfoService.findListByParam(orderInfoQuery);
                            for (OrderInfo orderInfo : orderInfoList) {
                                try {
                                    OrderLogisticsInfo logisticsInfo = new OrderLogisticsInfo();
                                    logisticsInfo.setLogisticsNo("SF" + StringTools.getRandomNumber(10));
                                    logisticsInfo.setLogisticsCompany("椤轰赴");
                                    logisticsInfo.setOrderId(orderInfo.getOrderId());
                                    orderLogisticsInfoService.delivery(logisticsInfo);
                                } catch (Exception e) {
                                    log.error("鑷姩鍙戣揣澶辫触", e);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("澶勭悊瓒呮椂璁㈠崟澶辫触", e);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    /**
     * 绯荤粺鑷姩纭鏀惰揣
     */
    @PostConstruct
    public void consumeConfirmOrder() {
        ExecutorServiceSingletonEnum.INSTANCE.getExecutorService().execute(() -> {
            while (true) {
                try {
                        Set<String> queuePayOrderList = redisComponent.getTimeOutOrder(Constants.REDIS_KEY_ORDER_DELAY_QUEUE_CONFIRM);
                    if (queuePayOrderList == null || queuePayOrderList.isEmpty()) {
                        Thread.sleep(5000);
                        continue;
                    }
                    for (String orderId : queuePayOrderList) {
                        if (redisComponent.removeTimeOutOrder(Constants.REDIS_KEY_ORDER_DELAY_QUEUE_CONFIRM, orderId) > 0) {
                            try {
                                orderInfoService.confirmOrder(null, orderId);
                            } catch (Exception e) {
                                log.error("鑷姩纭鏀惰揣澶辫触", e);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("鑷姩纭鏀惰揣澶辫触", e);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    @PostConstruct
    public void consumeLogistics() {
        ExecutorServiceSingletonEnum.INSTANCE.getExecutorService().execute(() -> {
            while (true) {
                try {
                    Set<String> queueOrderList = redisComponent.getTimeOuterLogistics();
                    if (queueOrderList == null || queueOrderList.isEmpty()) {
                        Thread.sleep(5000);
                        continue;
                    }
                    for (String orderId : queueOrderList) {
                        if (redisComponent.removeTimeOuterLogistics(orderId) > 0) {
                            orderLogisticsInfoService.mockOrderlogistics(orderId);
                        }
                    }
                } catch (Exception e) {
                    log.error("妯℃嫙鐗╂祦淇℃伅澶辫触", e);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
}

