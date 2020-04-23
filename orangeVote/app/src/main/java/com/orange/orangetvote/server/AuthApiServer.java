package com.orange.orangetvote.server;

import com.orange.orangetvote.basic.enums.BaseEnums;
import java.io.Serializable;

public enum  AuthApiServer implements BaseEnums {

    Login("login");

    AuthApiServer(String url){
        this.url = url;
    }

    private String url;

    @Override
    public Serializable valueOf() {
        return url;
    }

    @Override
    public String valueOfName() {
        return url;
    }
}
