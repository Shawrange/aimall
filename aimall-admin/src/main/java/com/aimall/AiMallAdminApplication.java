package com.aimall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.aimall"})
@MapperScan(basePackages = {"com.aimall.mappers"})
@EnableTransactionManagement
@EnableScheduling
public class AiMallAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiMallAdminApplication.class, args);
    }
}

