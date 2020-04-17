package com.orange.orangetvote.entity;

import com.orange.orangetvote.basic.base.BaseResponseEntity;

public class VoteOptionResponseEntity extends BaseResponseEntity {

    private boolean select;
    private String text;
    private String value;

    public VoteOptionResponseEntity(boolean select, String text, String value) {
        this.select = select;
        this.text = text;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public boolean isSelect() {
        return select;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
