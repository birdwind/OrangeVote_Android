package com.orange.orangetvote.view.viewCallback;

import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.team.TeamResponse;
import com.orange.orangetvote.response.vote.VoteDetailResponse;
import java.util.List;

public interface UpdateVoteView extends BaseView {

    void loadTeamListSuccess(List<TeamResponse> teamListResponse);

    void loadVoteDetailSuccess(VoteDetailResponse voteDetailResponse);

}
