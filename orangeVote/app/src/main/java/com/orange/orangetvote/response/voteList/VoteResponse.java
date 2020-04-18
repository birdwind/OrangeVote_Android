package com.orange.orangetvote.response.voteList;

import com.orange.orangetvote.basic.response.AbstractResponse;

import java.util.List;

public class VoteResponse extends AbstractResponse {

    private List<VoteResponseEntity> response;

    public List<VoteResponseEntity> getResponse() {
        return response;
    }

    public static class VoteResponseEntity extends AbstractResponse {
        private String text;
        private String value;
        private String content;
        private int multiSelection;
        private boolean isAllowAdd;
        private boolean isSign;
        private List<VoteOptionResponse> option;

        public VoteResponseEntity(String text, String value, String content, int multiSelection, boolean isAllowAdd, boolean isSign, List<VoteOptionResponse> option) {
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

        public List<VoteOptionResponse> getOption() {
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

        public void setOption(List<VoteOptionResponse> option) {
            this.option = option;
        }

        public void setSign(boolean sign) {
            isSign = sign;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
