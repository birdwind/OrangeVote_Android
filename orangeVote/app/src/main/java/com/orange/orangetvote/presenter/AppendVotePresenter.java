package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.response.appendVote.TeamListServerResponse;
import com.orange.orangetvote.server.TeamApiServer;
import com.orange.orangetvote.view.callback.AppendVoteView;

public class AppendVotePresenter extends BasePresenter<AppendVoteView> {
    public AppendVotePresenter(AppendVoteView baseView) {
        super(baseView);
    }

    public void teamList() {
        initParamAndHeader();
        addDisposable(apiServer.executeGet(TeamApiServer.TEAM_LIST.valueOfName(), paramsMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                protected void onSuccess(String responseJson) {
                    TeamListServerResponse teamListResponse =
                        GsonUtils.parseJsonToBean(responseJson, TeamListServerResponse.class);
                    baseView.loadTeamListSuccess(teamListResponse.getResponse());
                }

                @Override
                protected void onError(String msg) {

                }

                @Override
                protected void onFieldsError(String responseJson) {

                }

            });
    }
}
