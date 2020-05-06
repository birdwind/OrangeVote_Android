package com.orange.orangetvote.request;

import com.orange.orangetvote.basic.request.AbstractRequest;
import com.orange.orangetvote.model.AddVoteOptionModel;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteOptionRequest extends AbstractRequest {

    public VoteOptionRequest(String optionUuid, String value) {
        this.optionUuid = optionUuid;
        this.value = value;
    }

    private String optionUuid;

    private String value;
}
