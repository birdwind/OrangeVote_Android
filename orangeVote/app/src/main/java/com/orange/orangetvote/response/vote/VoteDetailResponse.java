package com.orange.orangetvote.response.vote;

import com.orange.orangetvote.basic.response.BaseResponse;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteDetailResponse implements BaseResponse {

    private String voteUuid;

    private String voteName;

    private String content;

    private String team;

    private Date expiredDate;

    private Integer multiSelection;

    private Boolean isAllowAdd;

    private Boolean isOpen;

    private Boolean isSign;

    private Boolean isVoted;

    private Boolean isEnd;

    private Boolean isOwner;

    private List<VoteOptionDetailResponse> options;
}
