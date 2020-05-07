package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.request.AppendVoteRequest;
import com.orange.orangetvote.response.appendVote.AppendVoteResponse;
import com.orange.orangetvote.response.appendVote.AppendVoteServerResponse;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import com.orange.orangetvote.response.appendVote.TeamListServerResponse;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.server.TeamApiServer;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.AppendVoteView;
import java.util.List;
import okhttp3.ResponseBody;

public class AppendVotePresenter extends AbstractPresenter<AppendVoteView> {
    public AppendVotePresenter(AppendVoteView baseView) {
        super(baseView);
    }

    public void teamList() {
        initParamAndHeader();
        addDisposable(apiServer.executeGet(TeamApiServer.TEAM_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<ResponseBody, TeamListServerResponse, TeamListResponse, FieldErrorResponse>(baseView,
                TeamListServerResponse.class) {

                @Override
                public void onSuccess(List<TeamListResponse> responseList) {
                    baseView.loadTeamListSuccess(responseList);
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> responseFieldErrorList) {

                }

            });
    }

    public void appendVote(AppendVoteRequest appendVoteRequest) {
        initParamAndHeader();
        packageToParamsMap(appendVoteRequest);

        for (String key : paramsMap.keySet()) {
            LogUtils.e(key + " : " + paramsMap.get(key));
        }

        addDisposable(apiServer.executePut(VoteApiServer.APPEND.valueOfName(), requestBody, headerMap),
            new AbstractObserver<ResponseBody, AppendVoteServerResponse, AppendVoteResponse, FieldErrorResponse>(
                baseView, AppendVoteServerResponse.class) {

                @Override
                public void onSuccess(List<AppendVoteResponse> responseList) {
                    baseView.onAppendSuccess();
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> responseFieldErrorList) {

                }
            });
    }
}
