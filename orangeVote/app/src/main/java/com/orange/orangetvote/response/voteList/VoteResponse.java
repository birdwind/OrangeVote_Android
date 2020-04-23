package com.orange.orangetvote.response.voteList;

import com.orange.orangetvote.basic.response.BaseResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteResponse implements BaseResponse {
    private String text;

    private String value;

    private String content;

    private String team;

    private Integer multiSelection;

    private Boolean isAllowAdd;

    private Boolean isSign;

    private Boolean isVoted;

    private List<VoteOptionResponse> option;
}
