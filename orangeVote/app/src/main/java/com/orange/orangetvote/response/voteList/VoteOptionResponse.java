package com.orange.orangetvote.response.voteList;

import com.orange.orangetvote.basic.response.AbstractResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteOptionResponse extends AbstractResponse {

    private Boolean select;

    private String text;

    private String value;

}
