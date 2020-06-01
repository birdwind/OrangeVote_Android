package com.orange.orangetvote.view.viewCallback;

import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.team.TeamResponse;
import java.util.List;

public interface JoinTeamView extends BaseView {

    void onJoinTeamSucc(TeamResponse teamResponse);

    void loadTeam(List<TeamResponse> teamListResponse);
}
