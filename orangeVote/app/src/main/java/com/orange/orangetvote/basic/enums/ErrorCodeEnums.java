package com.orange.orangetvote.basic.enums;

import java.io.Serializable;

public enum ErrorCodeEnums implements BaseEnums {


    /**
     * 錯誤代碼枚舉
     *
     * UNKNOWN                  未知異常
     * TIMEOUT_ERROR            連接超時
     * NULL_POINT_EXCEPTION     空指針異常
     * SSL_ERROR                SSL異常
     * CAST_ERROR               Class轉換異常
     * PARSE_ERROR              解析異常
     * ILLEGAL_STATE_ERROR      非法數據異常
     * */

    UNKNOWN("未知異常", 1000),
    TIMEOUT_ERROR("連接超時,請稍後再試", 1001),
    NULL_POINT_EXCEPTION("空指針異常", 1002),
    SSL_ERROR("SSL異常", 1003),
    CAST_ERROR("Class轉換異常", 1004),
    PARSE_ERROR("解析異常", 1005),
    ILLEGAL_STATE_ERROR("非法數據異常", 1006);

    private String name;

    private int value;

    ErrorCodeEnums(String message, int value) {
        this.name = message;
        this.value = value;
    }

    @Override
    public Serializable valueOf() {
        return this.value;
    }

    @Override
    public String valueOfName() {
        return this.name;
    }

    public int getCode(){
        return (int)this.valueOf();
    }

    public String getMessage(){
        return this.valueOfName();
    }
}
