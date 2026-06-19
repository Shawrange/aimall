package com.aimall.utils;

import com.aimall.exception.BusinessException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

public class PasswordUtils {

    private static final String PBKDF2_PREFIX = "pbkdf2";
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int PBKDF2_ITERATIONS = 120_000;
    private static final int SALT_BYTES = 16;
    private static final int HASH_BITS = 256;
    private static final Pattern MD5_HEX = Pattern.compile("^[a-fA-F0-9]{32}$");
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private PasswordUtils() {
    }

    public static String hash(String password) {
        if (StringTools.isEmpty(password)) {
            throw new BusinessException("密码不能为空");
        }
        byte[] salt = new byte[SALT_BYTES];
        SECURE_RANDOM.nextBytes(salt);
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS);
        return String.join("$",
                PBKDF2_PREFIX,
                String.valueOf(PBKDF2_ITERATIONS),
                Base64.getEncoder().encodeToString(salt),
                Base64.getEncoder().encodeToString(hash));
    }

    public static boolean matches(String password, String storedPassword) {
        if (StringTools.isEmpty(password) || StringTools.isEmpty(storedPassword)) {
            return false;
        }
        if (storedPassword.startsWith(PBKDF2_PREFIX + "$")) {
            return matchesPbkdf2(password, storedPassword);
        }
        if (MD5_HEX.matcher(storedPassword).matches()) {
            return constantTimeEquals(storedPassword, password)
                    || constantTimeEquals(storedPassword, StringTools.encodeByMD5(password));
        }
        return constantTimeEquals(storedPassword, password)
                || constantTimeEquals(StringTools.encodeByMD5(storedPassword), password);
    }

    public static boolean shouldUpgrade(String storedPassword) {
        return !StringTools.isEmpty(storedPassword) && !storedPassword.startsWith(PBKDF2_PREFIX + "$");
    }

    private static boolean matchesPbkdf2(String password, String storedPassword) {
        String[] parts = storedPassword.split("\\$");
        if (parts.length != 4) {
            return false;
        }
        try {
            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[3]);
            byte[] actualHash = pbkdf2(password.toCharArray(), salt, iterations);
            return MessageDigest.isEqual(expectedHash, actualHash);
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        try {
            KeySpec spec = new PBEKeySpec(password, salt, iterations, HASH_BITS);
            return SecretKeyFactory.getInstance(PBKDF2_ALGORITHM).generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new BusinessException("密码处理失败");
        }
    }

    private static boolean constantTimeEquals(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        return MessageDigest.isEqual(first.getBytes(StandardCharsets.UTF_8), second.getBytes(StandardCharsets.UTF_8));
    }
}
