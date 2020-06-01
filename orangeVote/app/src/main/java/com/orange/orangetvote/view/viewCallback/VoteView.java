package com.orange.orangetvote.view.viewCallback;


import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.vote.VoteResponse;
import java.util.List;

public interface VoteView extends BaseView {

    void loadVoteListApiSuccess(List<VoteResponse> voteResponseList);

    void onVotedApiSuccess();
}
