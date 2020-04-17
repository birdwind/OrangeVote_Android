package com.orange.orangetvote.entity;

import com.orange.orangetvote.basic.base.BaseResponseEntity;

public class VoteResponseEntity extends BaseResponseEntity {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
