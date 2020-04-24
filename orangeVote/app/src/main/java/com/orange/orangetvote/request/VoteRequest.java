package com.orange.orangetvote.request;

import com.orange.orangetvote.basic.request.AbstractRequest;
import com.orange.orangetvote.model.AddVoteOptionModel;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest extends AbstractRequest {

    public VoteRequest(String voteUuid, List<String> optionUuids, List<AddVoteOptionModel> addOptions) {
        this.voteUuid = voteUuid;
        this.optionUuids = optionUuids;
        this.addOptions = addOptions;
    }

    private String voteUuid;

    private List<String> optionUuids;

    private List<AddVoteOptionModel> addOptions;
}
