package com.orange.orangetvote.view.callback;

import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import com.orange.orangetvote.response.vote.VoteDetailResponse;
import java.util.List;

public interface UpdateVoteView extends BaseView {

    void loadTeamListSuccess(List<TeamListResponse> teamListResponseList);

    void loadVoteDetailSuccess(VoteDetailResponse voteDetailResponse);

}
