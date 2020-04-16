package com.orange.orangetvote.basic.enums;

import java.io.Serializable;

public enum SystemErrorEnums implements BaseEnums {
    LAYOUT_RES_ERROR("Layout資源未設定", 2001);

    private String message;

    private int code;

    SystemErrorEnums(String message, int code){
        this.message = message;
        this.code = code;
    }

    @Override
    public Serializable valueOf() {
        return this.code;
    }

    @Override
    public String valueOfName() {
        return this.message;
    }

    public String getMessage(){
        return this.valueOfName();
    }

    public int getCode() {
        return (int)this.valueOf();
    }
}
