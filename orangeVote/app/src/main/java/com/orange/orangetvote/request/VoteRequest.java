package com.orange.orangetvote.request;

import com.orange.orangetvote.basic.request.AbstractRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest extends AbstractRequest {

    //TODO: 投票的請求form-data
    private String voteUuid;
}
