package com.orange.orangetvote.server;

import com.orange.orangetvote.basic.enums.BaseEnums;
import java.io.Serializable;

public enum PersonalApiServer implements BaseEnums {
    PERSONAL_INFO(""), PERSONAL_UPDATE("");

    private String url;

    PersonalApiServer(String url){
        this.url = "api/personal" + url;
    }

    @Override
    public Serializable valueOf() {
        return this.url;
    }

    @Override
    public String valueOfName() {
        return this.url;
    }
}
