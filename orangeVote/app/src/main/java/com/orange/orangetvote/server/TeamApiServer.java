package com.orange.orangetvote.server;

import com.orange.orangetvote.basic.enums.BaseEnums;
import java.io.Serializable;

public enum TeamApiServer implements BaseEnums {
    TEAM_LIST("/list");

    TeamApiServer(String url){
        this.url = "api/team" + url;
    }

    private String url;

    @Override
    public Serializable valueOf() {
        return this.url;
    }

    @Override
    public String valueOfName() {
        return this.url;
    }
}
