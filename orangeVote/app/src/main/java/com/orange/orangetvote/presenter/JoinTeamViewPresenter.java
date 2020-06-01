package com.orange.orangetvote.presenter;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.response.team.TeamResponse;
import com.orange.orangetvote.response.team.TeamServerResponse;
import com.orange.orangetvote.server.TeamApiServer;
import com.orange.orangetvote.view.viewCallback.JoinTeamView;
import java.util.List;
import okhttp3.ResponseBody;

public class JoinTeamViewPresenter extends AbstractPresenter<JoinTeamView> {
    public JoinTeamViewPresenter(JoinTeamView baseView) {
        super(baseView);
    }

    @Override
    public void fieldsErrorHandler(List<FieldErrorResponse> fieldErrorResponseList) {

    }

    public void loadTeam() {
        initMap();

        addDisposable(apiServer.executeGet(TeamApiServer.TEAM_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<ResponseBody, TeamServerResponse, TeamResponse, FieldErrorResponse>(baseView,
                TeamServerResponse.class) {

                @Override
                public void onSuccess(List<TeamResponse> responseList) {
                    baseView.loadTeam(responseList);
                }

                @Override
                public void onResponseError(String responseError) {

                }

                @Override
                public void onResponseFieldError(List<FieldErrorResponse> responseFieldErrorList) {

                }
            });
    }

    public void joinTeam(String teamUuid, String passCode) {
        initMap();
        paramsMap.put("pass_code", passCode);
        addDisposable(
            apiServer.executeGet(TeamApiServer.JOIN_TEAM.valueOfName().replace("{teamUuid}", teamUuid), paramsMap,
                headerMap),
            new AbstractObserver<ResponseBody, TeamServerResponse, TeamResponse, FieldErrorResponse>(baseView,
                TeamServerResponse.class) {
                @Override
                public void onSuccess(List<TeamResponse> responseList) {
                    baseView.onJoinTeamSucc(responseList.get(0));
                }

                @Override
                public void onResponseError(String responseError) {
                    if (responseError.equals("Error.Team.Joined")) {
                        baseView.showError(context.getString(R.string.error_join_team_joined));
                    }
                }

                @Override
                public void onResponseFieldError(List<FieldErrorResponse> responseFieldErrorList) {
                    fieldsErrorHandler(responseFieldErrorList);
                }
            });
    }
}
