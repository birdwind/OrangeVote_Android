package com.orange.orangetvote.request;

import com.orange.orangetvote.basic.request.AbstractRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppendVoteRequest extends AbstractRequest {

    public AppendVoteRequest(String voteUuid, String voteName, String content, Integer multiSelection, String expiredDate,
        String teamUuid, Boolean isAllowAdd, Boolean isOpen, Boolean isSign, List<VoteOptionRequest> options) {
        this.voteUuid = voteUuid;
        this.voteName = voteName;
        this.content = content;
        this.multiSelection = multiSelection;
        this.expiredDate = expiredDate;
        this.teamUuid = teamUuid;
        this.isAllowAdd = isAllowAdd;
        this.isOpen = isOpen;
        this.isSign = isSign;
        this.options = options;
        this.deleteOptions = new ArrayList<>();
    }

    private String voteUuid;

    private String voteName;

    private String content;

    private Integer multiSelection;

    private String expiredDate;

    private String teamUuid;

    private Boolean isAllowAdd;

    private Boolean isOpen;

    private Boolean isSign;

    private List<VoteOptionRequest> options;

    // TODO:刪除選項物件
    private List<VoteOptionRequest> deleteOptions;

}
