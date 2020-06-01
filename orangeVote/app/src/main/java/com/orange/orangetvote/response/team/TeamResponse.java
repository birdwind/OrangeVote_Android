package com.orange.orangetvote.response.team;

import com.orange.orangetvote.basic.response.AbstractResponse;
import com.orange.orangetvote.basic.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamResponse implements BaseResponse {

    private String teamUuid;

    private String teamValue;

    private String passCode;

    private boolean status;

}
