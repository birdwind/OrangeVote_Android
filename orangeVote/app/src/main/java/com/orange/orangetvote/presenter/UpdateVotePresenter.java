package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.request.AppendVoteRequest;
import com.orange.orangetvote.response.appendVote.AppendVoteResponse;
import com.orange.orangetvote.response.appendVote.AppendVoteServerResponse;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import com.orange.orangetvote.response.appendVote.TeamListServerResponse;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.server.TeamApiServer;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.AppendVoteView;
import com.orange.orangetvote.view.callback.UpdateVoteView;
import java.util.List;
import okhttp3.ResponseBody;

public class UpdateVotePresenter extends AbstractPresenter<UpdateVoteView> {
    public UpdateVotePresenter(UpdateVoteView baseView) {
        super(baseView);
    }

    public void teamList() {
        initMap();
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

    public void voteDetail(String voteUuid) {
//        initMap();
//        addDisposable(apiServer.executeGet(VoteApiServer.TEAM_DETAIL.valueOfName() + voteUuid, paramsMap, headerMap),
//            new AbstractObserver() {
//                @Override
//                public void onSuccess(List responseList) {
//
//                }
//
//                @Override
//                public void onFieldsError(List responseFieldErrorList) {
//
//                }
//
//                @Override
//                public void onNext(Object o) {
//
//                }
//            });
    }

}
