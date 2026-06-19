package com.aimall.utils;

public class SnowFlakeUtils {

    // 璧峰鏃堕棿鎴筹紙2025-05-01锛?
    private final static long START_STMP = 1746028800000L;

    // 鍚勯儴鍒嗕綅鏁?
    private final static long SEQUENCE_BIT = 12; // 搴忓垪鍙蜂綅鏁?
    private final static long MACHINE_BIT = 5;   // 鏈哄櫒鏍囪瘑浣嶆暟
    private final static long DATACENTER_BIT = 5; // 鏁版嵁涓績浣嶆暟

    // 鏈€澶у€?
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // 绉讳綅鍋忕Щ
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private static long datacenterId = 1;  // 鏁版嵁涓績ID
    private static long machineId = 1;    // 鏈哄櫒ID
    private static long sequence = 0L; // 搴忓垪鍙?
    private static long lastStmp = -1L; // 涓婃鏃堕棿鎴?

    /**
     * 鐢熸垚涓嬩竴涓狪D
     */
    public static synchronized long nextId() {
        long currStmp = getNewstmp();

        if (currStmp < lastStmp) {
            throw new RuntimeException("鏃堕挓鍥炴嫧锛屾嫆缁濈敓鎴怚D");
        }

        if (currStmp == lastStmp) {
            // 鐩稿悓姣鍐咃紝搴忓垪鍙疯嚜澧?
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 鍚屼竴姣鍐呭簭鍒楁暟宸茶揪鏈€澶?
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            // 涓嶅悓姣鍐咃紝搴忓垪鍙风疆涓?
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT // 鏃堕棿鎴抽儴鍒?
                | datacenterId << DATACENTER_LEFT      // 鏁版嵁涓績閮ㄥ垎
                | machineId << MACHINE_LEFT            // 鏈哄櫒鏍囪瘑閮ㄥ垎
                | sequence;                             // 搴忓垪鍙烽儴鍒?
    }

    // 鑾峰彇涓嬩竴姣
    private static long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    // 鑾峰彇褰撳墠鏃堕棿鎴?
    private static long getNewstmp() {
        return System.currentTimeMillis();
    }
}
