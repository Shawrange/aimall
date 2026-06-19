package com.aimall.utils;


import com.aimall.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    /**
     *
     * @param dateString 鏃ユ湡鏃堕棿瀛楃涓?
     * @param format     鏍煎紡瀛楃涓诧紙濡?yyyy-MM-dd HH:mm:ss"锛?
     * @return 杞崲鍚庣殑Date瀵硅薄锛屽け璐ヨ繑鍥瀗ull
     */
    public static Date parse(String dateString, String format) {
        if (dateString == null || dateString.trim().isEmpty() || format == null || format.trim().isEmpty()) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime localDateTime;
            // 鏍规嵁鏍煎紡鍒ゆ柇鏄棩鏈熴€佹椂闂磋繕鏄棩鏈熸椂闂?
            Boolean hasDate = format.contains("y") || format.contains("M") || format.contains("d");
            Boolean hasTime = format.contains("H") || format.contains("h") || format.contains("m") || format.contains("s");
            if (hasDate && hasTime) {
                localDateTime = LocalDateTime.parse(dateString, formatter);
            } else if (hasDate) {
                LocalDate localDate = LocalDate.parse(dateString, formatter);
                localDateTime = localDate.atStartOfDay(); // 琛ュ厖鏃堕棿涓?0:00:00
            } else {
                LocalTime localTime = LocalTime.parse(dateString, formatter);
                localDateTime = LocalDate.now().atTime(localTime); // 琛ュ厖褰撳墠鏃ユ湡
            }
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            log.error("杞崲鏃ユ湡寮傚父", e);
            return null;
        }
    }

    /**
     * 灏咲ate瀵硅薄鎸夋寚瀹氭牸寮忚浆鎹负瀛楃涓?
     * 鍐呴儴鍏堣浆鎹负LocalDateTime锛屽啀杩涜鏍煎紡鍖?
     *
     * @param date   瑕佽浆鎹㈢殑Date瀵硅薄
     * @param format 鏍煎紡瀛楃涓诧紙濡?yyyy-MM-dd"锛?
     * @return 鏍煎紡鍖栧悗鐨勬棩鏈熷瓧绗︿覆锛屽弬鏁版棤鏁堣繑鍥炵┖瀛楃涓?
     */
    public static String format(Date date, String format) {
        if (date == null || format == null || format.trim().isEmpty()) {
            return null;
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.atZone(ZoneId.systemDefault()).format(formatter);
        } catch (IllegalArgumentException e) {
            log.error("鏃ユ湡鏍煎紡鍖栧紓甯?, e);
            return null;
        }
    }


    public static String getMinAfter(int min, String pattern) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, min);
        return format(c.getTime(), pattern);
    }

    public static String getBeforeDay(int day, String pattern) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -day);
        return format(c.getTime(), pattern);
    }

    public static List<String> getDateRange(String startDateStr, String endDateStr, String formatter) {
        List<String> dates = new ArrayList<>();
        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern(formatter));
        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern(formatter));
        // 纭繚寮€濮嬫棩鏈熶笉鏅氫簬缁撴潫鏃ユ湡
        if (startDate.isAfter(endDate)) {
            throw new BusinessException("寮€濮嬫棩鏈熶笉鑳芥櫄浜庣粨鏉熸棩鏈?);
        }
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate.format(DateTimeFormatter.ofPattern(formatter)));
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }
}

