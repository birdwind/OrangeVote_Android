package com.orange.orangetvote.response.voteRecord;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.orange.orangetvote.basic.response.BaseResponse;
import com.orange.orangetvote.response.vote.VoteOptionResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyVoteResponse implements BaseResponse, MultiItemEntity {

    private String text;

    private String value;

    private String content;

    private int multiSelection;

    private String team;

    private String expireDate;

    private boolean isAllowAdd;

    private boolean isSign;

    private boolean isEnd;

    private boolean isVoted;

     private List<VoteOptionResponse> options;

     private List<VoteOptionDetailResponse> optionsDetail;

    @Override
    public int getItemType() {
        if(isEnd) {
            return 2;
        }else{
            if (isVoted) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
