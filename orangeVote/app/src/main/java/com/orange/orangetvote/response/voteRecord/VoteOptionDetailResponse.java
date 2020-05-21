package com.orange.orangetvote.response.voteRecord;

import com.orange.orangetvote.basic.response.BaseResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteOptionDetailResponse implements BaseResponse {

    private String text;

    private String value;

    private int percent;

    private List<String> selecteds;

}
