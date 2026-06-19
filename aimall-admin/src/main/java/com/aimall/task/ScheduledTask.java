package com.aimall.task;

import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.service.StatisticsInfoService;
import com.aimall.utils.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTask {

    @Resource
    private StatisticsInfoService statisticsInfoService;

    @Scheduled(cron = "0 0 1 * * ?") //姣忓ぉ1鐐规墽琛?
    public void statisticsTask() {
        String yesterday = DateUtil.getBeforeDay(1, DateTimePatternEnum.YYYY_MM_DD.getPattern());
        statisticsInfoService.statisticsData(yesterday);
    }
}
