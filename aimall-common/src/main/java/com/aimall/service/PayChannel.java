package com.aimall.service;

import com.aimall.entity.dto.PayInfoDTO;
import com.aimall.entity.dto.PayOrderNotifyDTO;
import com.aimall.entity.enums.PayChannelEnum;

import java.math.BigDecimal;
import java.util.Map;

public interface PayChannel {

    /**
     * 鑾峰彇鏀粯淇℃伅
     *
     * @param payChannelEnum
     * @param payOrderId
     * @param subject
     * @param amount
     * @return
     */
    PayInfoDTO getPayUrl(PayChannelEnum payChannelEnum, String payOrderId, String subject, BigDecimal amount);

    /**
     * 鏍￠獙鏀粯鍥炶皟
     *
     * @param requestParams
     * @param jsonBody
     * @return
     */
    PayOrderNotifyDTO payNotify(Map<String, String> requestParams, String jsonBody);


    /**
     * 鏌ヨ璁㈠崟
     *
     * @param payOrderId
     * @return
     */
    PayOrderNotifyDTO queryOrder(String payOrderId);

    /**
     * 閫€娆?
     *
     * @param payOrderId
     * @param refundAmount
     */
    void refund(String sourcePayOrderId, String payOrderId, BigDecimal refundAmount);

    /**
     * 鍏抽棴璁㈠崟
     *
     * @param payOrderId
     */
    void closeOrder(String payOrderId);
}

