package com.orange.orangetvote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamModel {
    public TeamModel(String teamUuid, String teamValue) {
        this.teamUuid = teamUuid;
        this.teamValue = teamValue;
    }

    private String teamUuid;

    private String teamValue;
}
