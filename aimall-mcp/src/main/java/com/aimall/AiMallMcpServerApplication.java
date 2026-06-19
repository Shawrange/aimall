package com.aimall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@MapperScan(basePackages = {"com.aimall.mappers"})
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.aimall"})
public class AiMallMcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiMallMcpServerApplication.class, args);
    }

}
