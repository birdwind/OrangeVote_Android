package com.orange.orangetvote.view.callback;

import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import java.util.List;

public interface AppendVoteView extends BaseView {

    void loadTeamListSuccess(List<TeamListResponse> teamListResponseList);

    void onAppendSuccess();
}
