package com.aimall.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.aimall.entity.config.AppConfig;
import com.aimall.entity.dto.PayInfoDTO;
import com.aimall.entity.dto.PayOrderNotifyDTO;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.entity.enums.PayChannelEnum;
import com.aimall.exception.BusinessException;
import com.aimall.service.PayChannel;
import com.aimall.utils.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Service("payChannel4Alipay")
public class PayChannel4Alipay implements PayChannel {

    static {
        // 璁剧疆SSL鍗忚鐗堟湰
        System.setProperty("https.protocols", "TLSv1.2");
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
    }

    private static final String TRADE_STATE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 浜ゆ槗涓嶅瓨鍦?
     */
    private static final String TRADE_NOT_EXIST = "ACQ.TRADE_NOT_EXIST";

    private static final String NOTIFY_URL = "/api/notify/alipayNotify";

    @Resource
    private AppConfig appConfig;

    @Override
    public PayInfoDTO getPayUrl(PayChannelEnum payChannelEnum, String payOrderId, String subject, BigDecimal amount) {
        try {
            // 鍒濆鍖朣DK
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            // 鏋勯€犺姹傚弬鏁颁互璋冪敤鎺ュ彛
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            // 璁剧疆鍟嗘埛璁㈠崟鍙?
            model.setOutTradeNo(payOrderId);
            // 璁剧疆璁㈠崟鎬婚噾棰?
            model.setTotalAmount(amount.toString());
            // 璁剧疆璁㈠崟鏍囬
            model.setSubject(subject);
            //璁㈠崟杩囨湡鏃堕棿
            model.setTimeExpire(DateUtil.getMinAfter(appConfig.getOrderExpireMinute(), DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));

            request.setBizModel(model);

            //瀹為檯寮€鍙戜細璁剧疆寮傛閫氱煡鍥炶皟淇℃伅锛屼互渚垮疄鏃惰幏鍙栬鍗曟敮浠樼粨鏋?
            request.setNotifyUrl(appConfig.getProjectDomain() + NOTIFY_URL);
            String payInfo = null;
            switch (payChannelEnum) {
                case ALIPAY_PC:
                    model.setProductCode("FAST_INSTANT_TRADE_PAY");
                    AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
                    if (!response.isSuccess()) {
                        throw new BusinessException("鑾峰彇鏀粯淇℃伅澶辫触");
                    }
                    payInfo = response.getBody();
                    break;
            }
            return new PayInfoDTO(payInfo, payOrderId, amount);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("鏀粯瀹濇敮浠樿幏鍙栨敮浠樹俊鎭け璐?, e);
            throw new BusinessException("鑾峰彇鏀粯淇℃伅澶辫触");
        }
    }

    private AlipayConfig getAlipayConfig() {
        AlipayConfig config = new AlipayConfig();
        //鏀粯瀹濈綉鍏?
        config.setServerUrl(appConfig.getAlipayServerUrl());
        //搴旂敤ID
        config.setAppId(appConfig.getAlipayAppid());
        //搴旂敤绉侀挜淇℃伅
        config.setPrivateKey(appConfig.getAlipayAppPrivateKey());
        //搴旂敤鍏挜璇佷功鏈湴鍦板潃
        config.setAppCertPath(appConfig.getProjectFolder() + appConfig.getAlipayAppCertPath());
        //鏀粯瀹濆叕閽ヨ瘉涔?alipayPublicCert.crt
        config.setAlipayPublicCertPath(appConfig.getProjectFolder() + appConfig.getAlipayPublicCertPath());
        //鏀粯瀹濇牴璇佷功鏈湴鍦板潃
        config.setRootCertPath(appConfig.getProjectFolder() + appConfig.getAlipayRootCertPath());
        config.setCharset("UTF8");
        config.setSignType("RSA2");
        return config;
    }

    @Override
    public PayOrderNotifyDTO payNotify(Map<String, String> requestParams, String jsonBody) {
        try {
            requestParams.remove("sign_type");
            Boolean signCheckResult = AlipaySignature.rsaCertCheckV2(requestParams, appConfig.getAlipayAppCertPath(), "UTF-8", "RSA2");
            if (!signCheckResult) {
                throw new BusinessException("鏀粯瀹濆洖璋冩牎楠屽け璐?);
            }
        } catch (AlipayApiException e) {
            log.error("鏀粯瀹濆洖璋冩楠屽け璐?, e);
            throw new BusinessException("鏀粯瀹濆洖璋冩牎楠屽け璐?);
        }
        String payOrderId = requestParams.get("out_trade_no");
        String channelOrderId = requestParams.get("trade_no");
        String status = String.valueOf(requestParams.get("trade_status"));
        if (!TRADE_STATE_SUCCESS.equalsIgnoreCase(status)) {
            log.info("鏀粯瀹濆洖璋冨湴鍧€鐘舵€佷笉涓簊uccess锛屼笉鍋氬鐞嗭紝璁㈠崟鍙凤細{}", payOrderId);
            return null;
        }
        return new PayOrderNotifyDTO(payOrderId, channelOrderId);
    }

    @Override
    public PayOrderNotifyDTO queryOrder(String payOrderId) {
        try {
            // 鍒濆鍖朣DK
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            // 璁剧疆鍟嗘埛璁㈠崟鍙?
            model.setOutTradeNo(payOrderId);
            request.setBizModel(model);
            AlipayTradeQueryResponse response = alipayClient.certificateExecute(request);

            //璁㈠崟鏈敮浠?
            if (!response.isSuccess() || !TRADE_STATE_SUCCESS.equals(response.getTradeStatus())) {
                return null;
            }
            log.info("鏌ヨ鏀粯瀹濊鍗?{},杩斿洖缁撴灉:{}", payOrderId, response.getBody());
            return new PayOrderNotifyDTO(payOrderId, response.getTradeNo());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("鏀粯瀹濊幏鍙栨敮浠樹俊鎭け璐?, e);
            throw new BusinessException("鑾峰彇鏀粯淇℃伅澶辫触");
        }
    }

    @Override
    public void refund(String sourcePayOrderId, String payOrderId, BigDecimal refundAmount) {
        try {
            // 鍒濆鍖朣DK
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            // 璁剧疆鍟嗘埛璁㈠崟鍙?
            model.setOutTradeNo(sourcePayOrderId);
            model.setOutRequestNo(payOrderId);
            model.setRefundAmount(refundAmount.toString());
            request.setBizModel(model);
            AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);
            log.info("鏀粯瀹濋€€娆?{},杩斿洖缁撴灉:{}", payOrderId, response.getBody());
            if (!response.isSuccess()) {
                throw new BusinessException("閫€娆惧け璐?);
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("鏀粯瀹濋€€娆惧け璐?, e);
            throw new BusinessException("鏀粯瀹濋€€娆惧け璐?);
        }
    }

    @Override
    public void closeOrder(String payOrderId) {
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(getAlipayConfig());
            AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            AlipayTradeCloseModel model = new AlipayTradeCloseModel();
            // 璁剧疆鍟嗘埛璁㈠崟鍙?
            model.setOutTradeNo(payOrderId);
            request.setBizModel(model);
            AlipayTradeCloseResponse response = alipayClient.certificateExecute(request);
            if (!response.isSuccess() && !TRADE_NOT_EXIST.equals(response.getSubCode())) {
                throw new BusinessException("璁㈠崟鍏抽棴澶辫触");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("鏀粯瀹濊鍗曞叧闂け璐?, e);
            throw new BusinessException("璁㈠崟鍏抽棴澶辫触");
        }
    }
}

