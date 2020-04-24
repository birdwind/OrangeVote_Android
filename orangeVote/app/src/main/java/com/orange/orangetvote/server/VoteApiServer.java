package com.orange.orangetvote.server;

import com.orange.orangetvote.basic.enums.BaseEnums;

import java.io.Serializable;

public enum VoteApiServer implements BaseEnums {

    VOTE_LIST("/list"), VOTE("/option");

    private String Url;

    VoteApiServer(String Url){
        this.Url = "api/vote" + Url;
    }

    @Override
    public Serializable valueOf() {
        return this.Url;
    }

    @Override
    public String valueOfName() {
        return this.Url;
    }
}
