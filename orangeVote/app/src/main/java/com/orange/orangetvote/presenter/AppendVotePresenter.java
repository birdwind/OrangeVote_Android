package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.request.AppendVoteRequest;
import com.orange.orangetvote.response.appendVote.AppendVoteResponse;
import com.orange.orangetvote.response.appendVote.AppendVoteServerResponse;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.response.team.TeamResponse;
import com.orange.orangetvote.response.team.TeamServerResponse;
import com.orange.orangetvote.server.TeamApiServer;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.viewCallback.AppendVoteView;
import java.util.List;

public class AppendVotePresenter extends AbstractPresenter<AppendVoteView> {
    public AppendVotePresenter(AppendVoteView baseView) {
        super(baseView);
    }

    public void teamList() {
        initMap();
        addDisposable(apiServer.executeGet(TeamApiServer.TEAM_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<TeamServerResponse, TeamResponse>(baseView, TeamServerResponse.class) {

                @Override
                public void onSuccess(List<TeamResponse> responseList) {
                    baseView.loadTeamListSuccess(responseList);
                }

                @Override
                public void onResponseError(String responseError) {

                }

                @Override
                public void onResponseFieldError(List<FieldErrorResponse> responseFieldErrorList) {

                }

            });
    }

    public void appendVote(AppendVoteRequest appendVoteRequest) {
        initMap();

        fieldMap = parseObjectToHashMap(appendVoteRequest);

        addDisposable(
            apiServer.executePutFormUrlEncode(VoteApiServer.APPEND.valueOfName(), paramsMap, fieldMap, headerMap),
            new AbstractObserver<AppendVoteServerResponse, AppendVoteResponse>(baseView,
                AppendVoteServerResponse.class) {

                @Override
                public void onSuccess(List<AppendVoteResponse> responseList) {
                    baseView.onAppendSuccess();
                }

                @Override
                public void onResponseError(String responseError) {

                }

                @Override
                public void onResponseFieldError(List<FieldErrorResponse> responseFieldErrorList) {

                }
            });
    }

    @Override
    public void fieldsErrorHandler(List<FieldErrorResponse> fieldErrorResponseList) {

    }
}
