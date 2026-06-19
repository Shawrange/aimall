package com.aimall.utils;

import com.aimall.constants.Constants;
import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.exception.BusinessException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;


public class StringTools {

    private static final String DECIMAL_FORMAT = "0.00";
    private static final Pattern WINDOWS_ABSOLUTE_PATH = Pattern.compile("^[a-zA-Z]:[\\\\/].*");


    public static void checkParam(Object param) {
        try {
            Field[] fields = param.getClass().getDeclaredFields();
            boolean notEmpty = false;
            for (Field field : fields) {
                String methodName = "get" + StringTools.upperCaseFirstLetter(field.getName());
                Method method = param.getClass().getMethod(methodName);
                Object object = method.invoke(param);
                if (object != null && object instanceof String && !StringTools.isEmpty(object.toString())
                        || object != null && !(object instanceof String)) {
                    notEmpty = true;
                    break;
                }
            }
            if (!notEmpty) {
                throw new BusinessException("澶氬弬鏁版洿鏂帮紝鍒犻櫎锛屽繀椤绘湁闈炵┖鏉′欢");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("鏍￠獙鍙傛暟鏄惁涓虹┖澶辫触");
        }
    }

    public static String upperCaseFirstLetter(String field) {
        if (isEmpty(field)) {
            return field;
        }
        //濡傛灉绗簩涓瓧姣嶆槸澶у啓锛岀涓€涓瓧姣嶄笉澶у啓
        if (field.length() > 1 && Character.isUpperCase(field.charAt(1))) {
            return field;
        }
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || "null".equals(str) || "\u0000".equals(str)) {
            return true;
        } else if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static String encodeByMD5(String originString) {
        return StringTools.isEmpty(originString) ? null : DigestUtils.md5Hex(originString);
    }

    public static final String getRandomString(Integer count) {
        return RandomStringUtils.random(count, true, true);
    }

    public static final String getRandomNumber(Integer count) {
        return RandomStringUtils.random(count, false, true);
    }

    public static String getFileSuffix(String fileName) {
        if (StringTools.isEmpty(fileName) || !fileName.contains(".") || fileName.endsWith(".")) {
            throw new BusinessException("文件后缀不合法");
        }
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return suffix;
    }

    public static boolean pathIsOk(String path) {
        if (StringTools.isEmpty(path)) {
            return true;
        }
        String normalizedPath = path.replace('\\', '/');
        if (normalizedPath.startsWith("/")
                || normalizedPath.contains("../")
                || normalizedPath.contains("/..")
                || normalizedPath.equals("..")
                || normalizedPath.contains("%2e")
                || normalizedPath.contains("%2E")
                || WINDOWS_ABSOLUTE_PATH.matcher(path).matches()) {
            return false;
        }
        return true;
    }

    public static BigDecimal convertYuan2fen4BigDecimal(BigDecimal amount) {
        BigDecimal fen = amount.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);
        return fen;
    }

    public static final String createProductOrderId() {
        return DateUtil.format(new Date(), DateTimePatternEnum.YYYYMMDDHHMMSS.getPattern()) + getRandomString(16).toUpperCase();
    }

    public static final String createPayOrderId() {
        return StringTools.getRandomNumber(Constants.LENGTH_30);
    }

    public static Integer getRandomNumberRange(Integer min, Integer max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}

