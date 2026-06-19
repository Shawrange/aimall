package com.aimall.entity.enums;


public enum ResponseCodeEnum {
    CODE_200(200, "璇锋眰鎴愬姛"), CODE_404(404, "璇锋眰鍦板潃涓嶅瓨鍦?), CODE_600(600, "璇锋眰鍙傛暟閿欒"), CODE_601(601, "淇℃伅宸茬粡瀛樺湪"), CODE_602(602, "鏂囦欢涓嶅瓨鍦?), CODE_603(603, "鏁版嵁杞崲澶辫触"), CODE_604(604,
            "鎺ュ彛璇锋眰瓒呮椂"), CODE_605(605, "鎺ュ彛璇锋眰澶辫触"), CODE_500(500, "鏈嶅姟鍣ㄨ繑鍥為敊璇紝璇疯仈绯荤鐞嗗憳"), CODE_901(901, "鐧诲綍瓒呮椂");

    private Integer code;

    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

