package com.orange.orangetvote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUpdateVoteOptionModel {

    public AddUpdateVoteOptionModel(String value) {
        this.value = value;
    }

    public AddUpdateVoteOptionModel(String optionUuid, String value) {
        this.value = value;
        this.optionUuid = optionUuid;
    }

    private String optionUuid = null;

    private String value;

    private boolean isUpdate = false;
}
