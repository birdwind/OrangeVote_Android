package com.orange.orangetvote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUpdateVoteOptionModel {

    public AddUpdateVoteOptionModel(String value) {
        this.value = value;
        this.isOwner = false;
    }

    public AddUpdateVoteOptionModel(String optionUuid, String value, Boolean isOwner) {
        this.value = value;
        this.optionUuid = optionUuid;
        this.isOwner = isOwner;
    }

    private String optionUuid = null;

    private String value;

    private Boolean isUpdate = false;

    private Boolean isOwner;
}
