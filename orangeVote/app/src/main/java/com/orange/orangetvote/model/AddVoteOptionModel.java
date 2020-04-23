package com.orange.orangetvote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddVoteOptionModel {

    public AddVoteOptionModel(String value){
        this.value = value;
        this.optionUuid = null;
    }

    private String optionUuid;

    private String value;
}
