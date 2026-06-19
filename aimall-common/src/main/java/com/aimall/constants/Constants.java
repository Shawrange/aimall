package com.aimall.constants;

public class Constants {

    //姝ｅ垯
    public static final String REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*_]{8,18}$";
    public static final String ZERO_STR = "0";
    public static final Integer LENGTH_5 = 5;
    public static final Integer LENGTH_10 = 10;
    public static final Integer LENGTH_15 = 15;
    public static final Integer LENGTH_30 = 30;

    public static final String PING = "ping";

    public static final String FILE_FOLDER_FILE = "file/";

    public static final String TOKEN_WEB = "token";


    /**
     * redis key 鐩稿叧
     */

    /**
     * 杩囨湡鏃堕棿 1鍒嗛挓
     */
    public static final Long REDIS_KEY_EXPIRES_ONE_MIN = 60L;

    /**
     * 杩囨湡鏃堕棿 1澶?
     */
    public static final Long REDIS_KEY_EXPIRES_DAY = REDIS_KEY_EXPIRES_ONE_MIN * 60 * 24;

    public static final Integer REDIS_KEY_EXPIRES_HEART_BEAT = 6;

    private static final String REDIS_KEY_PREFIX = "aimall:";

    public static final String TOKEN_ADMIN = "adminToken";

    public static final String REDIS_KEY_TOKEN_ADMIN = REDIS_KEY_PREFIX + "token:admin:";

    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX + "checkcode:";

    public static final String REDIS_KEY_TOKEN_WEB = REDIS_KEY_PREFIX + "token:web:";

    public static final String REDIS_KEY_TOKEN_USERID_WEB = REDIS_KEY_PREFIX + "token:web:userId:";

    public static final String REDIS_KEY_WS_USER_HEART_BEAT = REDIS_KEY_PREFIX + "ws:user:heartbeat";


    public static final String REDIS_KEY_CATEGORY_LIST = REDIS_KEY_PREFIX + "category:list:";

    public static final String CART_PAY_NAME = "璐墿杞︽敮浠?%d浠跺晢鍝?;

    //鏀粯璁㈠崟寤舵椂闃熷垪
    public static final String REDIS_KEY_ORDER_DELAY_QUEUE = REDIS_KEY_PREFIX + "order:delay:queue:";

    //鑷姩鍙戣揣闃熷垪
    public static final String REDIS_KEY_ORDER_DELAY_QUEUE_DELIVERY = REDIS_KEY_PREFIX + "order:delay:queue:delivery:";

    public static final String REDIS_KEY_ORDER_DELAY_QUEUE_CONFIRM = REDIS_KEY_PREFIX + "order:delay:queue:confirm:";

    public static final String REDIS_KEY_SETTING_LOGISTICS = REDIS_KEY_PREFIX + "setting:logistics:";

    public static final String REDIS_KEY_ORDER_LOGISTICS_QUEUE = REDIS_KEY_PREFIX + "order:logistics:queue:";

    //鍚戦噺鏁版嵁搴撻槦鍒?
    public static final String REDIS_QUEUE_RAG_DATA = REDIS_KEY_PREFIX + "queue:rag:";

    public static final String REDIS_KEY_CANCEL_AGENT_MESSAGE = REDIS_KEY_PREFIX + "agent:message:";

    //鎻愮ず璇?
    public static final String REDIS_KEY_PROMPT = REDIS_KEY_PREFIX + "prompt:";

    public static final String IMAGE_THUMBNAIL_SUFFIX = "_thumbnail";

    //鏈€杩戣鍗曞ぉ鏁?
    public static final Integer LATEST_ORDER_DAYS = 15;

}

