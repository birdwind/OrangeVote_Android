package com.orange.orangetvote.view.viewCallback;

import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.team.TeamResponse;
import java.util.List;

public interface AppendVoteView extends BaseView {

    void loadTeamListSuccess(List<TeamResponse> teamListResponse);

    void onAppendSuccess();
}
