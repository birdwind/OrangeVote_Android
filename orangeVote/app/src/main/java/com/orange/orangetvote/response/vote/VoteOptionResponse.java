package com.orange.orangetvote.response.vote;

import com.orange.orangetvote.basic.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteOptionResponse implements BaseResponse {

    private Boolean select;

    private String text;

    private String value;

}
