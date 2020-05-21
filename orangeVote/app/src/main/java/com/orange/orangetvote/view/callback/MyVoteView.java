package com.orange.orangetvote.view.callback;

import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.voteRecord.MyVoteResponse;
import java.util.List;

public interface MyVoteView extends BaseView {

    void loadMyVoteList(List<MyVoteResponse> myVoteResponseList);

    void loadMyVotedList(List<MyVoteResponse> myVoteResponseList);
}
