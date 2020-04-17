package com.orange.orangetvote.response.voteList;

import com.orange.orangetvote.basic.base.BaseResponseEntity;

import java.util.List;

public class VoteResponseModel extends BaseResponseEntity {

    private String text;
    private String value;
    private String content;
    private int multiSelection;
    private boolean isAllowAdd;
    private boolean isSign;
    private List<VoteOptionResponseModel> option;

    public VoteResponseModel(String text, String value, String content, int multiSelection, boolean isAllowAdd, boolean isSign, List<VoteOptionResponseModel> option){
        this.text = text;
        this.value = value;
        this.content = content;
        this.multiSelection = multiSelection;
        this.isAllowAdd = isAllowAdd;
        this.isSign = isSign;
        this.option = option;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<VoteOptionResponseModel> getOption() {
        return option;
    }

    public String getContent() {
        return content;
    }

    public String getValue() {
        return value;
    }

    public boolean isAllowAdd() {
        return isAllowAdd;
    }

    public int isMultiSelection() {
        return multiSelection;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setAllowAdd(boolean allowAdd) {
        isAllowAdd = allowAdd;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMultiSelection(int multiSelection) {
        this.multiSelection = multiSelection;
    }

    public void setOption(List<VoteOptionResponseModel> option) {
        this.option = option;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
