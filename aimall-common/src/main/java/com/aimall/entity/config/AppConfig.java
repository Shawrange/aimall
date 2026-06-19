package com.aimall.entity.config;

import com.aimall.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("appConfig")
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    /**
     * websocket 绔彛
     */
    @Value("${ws.port:}")
    private Integer wsPort;
    /**
     * 鏂囦欢鐩綍
     */
    @Value("${project.folder:}")
    private String projectFolder;

    @Value("${admin.account:}")
    private String adminAccount;

    @Value("${admin.password:}")
    private String adminPassword;


    @Value("${project.domain:}")
    private String projectDomain;

    @Value("${admin.emails:}")
    private String adminEmails;

    //鏀粯瀹濆簲鐢ㄧ閽?
    @Value("${alipay.appPrivateKey:}")
    private String alipayAppPrivateKey;

    @Value("${alipay.appid:}")
    private String alipayAppid;

    @Value("${alipay.appCertPath:}")
    private String alipayAppCertPath;

    @Value("${alipay.alipayPublicCertPath:}")
    private String alipayPublicCertPath;

    @Value("${alipay.alipayRootCertPath:}")
    private String alipayRootCertPath;


    @Value("${alipay.serverUrl:}")
    private String alipayServerUrl;

    //璁㈠崟瓒呮椂
    @Value("${order.expire.minute:5}")
    private Integer orderExpireMinute;


    //鑷姩纭鏀惰揣
    @Value("${order.confirm.minute:15}")
    private Integer orderConfirmMinute;


    //鑷姩鏍￠獙璁㈠崟
    @Value("${project.auto-checkpay:false}")
    private Boolean autoCheckpay;


    //闄愬埗ai鑱婂ぉ杞暟
    @Value("${project.ai-chat-limit:0}")
    private Integer aiChatLimit;


    public String getProjectFolder() {
        if (!StringTools.isEmpty(projectFolder) && !projectFolder.endsWith("/")) {
            projectFolder = projectFolder + "/";
        }
        return projectFolder;
    }

    public String getAdminEmails() {
        return adminEmails;
    }

    public Integer getWsPort() {
        return wsPort;
    }

    public Integer getOrderExpireMinute() {
        return orderExpireMinute;
    }

    public String getProjectDomain() {
        return projectDomain;
    }


    public String getAlipayAppCertPath() {
        return alipayAppCertPath;
    }

    public String getAlipayPublicCertPath() {
        return alipayPublicCertPath;
    }

    public String getAlipayRootCertPath() {
        return alipayRootCertPath;
    }

    public Boolean getAutoCheckpay() {
        return autoCheckpay;
    }

    public String getAlipayAppPrivateKey() {
        return alipayAppPrivateKey;
    }

    public String getAlipayAppid() {
        return alipayAppid;
    }

    public String getAlipayServerUrl() {
        return alipayServerUrl;
    }

    public Integer getOrderConfirmMinute() {
        return orderConfirmMinute;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public Integer getAiChatLimit() {
        return aiChatLimit;
    }
}

