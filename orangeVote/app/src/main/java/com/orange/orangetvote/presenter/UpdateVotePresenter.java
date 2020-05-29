package com.orange.orangetvote.presenter;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.utils.ToastUtils;
import com.orange.orangetvote.request.UpdateVoteRequest;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import com.orange.orangetvote.response.appendVote.TeamListServerResponse;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.response.vote.VoteDetailResponse;
import com.orange.orangetvote.response.vote.VoteDetailServerResponse;
import com.orange.orangetvote.server.TeamApiServer;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.UpdateVoteView;
import java.util.List;
import okhttp3.ResponseBody;

public class UpdateVotePresenter extends AbstractPresenter<UpdateVoteView> {

    private String currentVoteUuid = "";

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
        currentVoteUuid = voteUuid;
        initMap();
        addDisposable(apiServer.executeGet(VoteApiServer.TEAM_DETAIL.valueOfName() + voteUuid, paramsMap, headerMap),
            new AbstractObserver<ResponseBody, VoteDetailServerResponse, VoteDetailResponse, FieldErrorResponse>(
                baseView, VoteDetailServerResponse.class) {
                @Override
                public void onSuccess(List<VoteDetailResponse> responseList) {
                    baseView.loadVoteDetailSuccess(responseList.get(0));
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> responseFieldErrorList) {
                    fieldsErrorHandler(responseFieldErrorList);
                }
            });
    }

    public void updateVote(UpdateVoteRequest updateVoteRequest) {
        initMap();
        fieldMap = parseObjectToHashMap(updateVoteRequest);

        addDisposable(
            apiServer.executePostFormUrlEncode(VoteApiServer.UPDATE.valueOfName(), paramsMap, fieldMap, headerMap),
            new AbstractObserver<ResponseBody, VoteDetailServerResponse, VoteDetailResponse, FieldErrorResponse>(
                baseView, VoteDetailServerResponse.class) {
                @Override
                public void onSuccess(List<VoteDetailResponse> responseList) {
                    baseView.loadVoteDetailSuccess(responseList.get(0));
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> responseFieldErrorList) {
                    fieldsErrorHandler(responseFieldErrorList);
                }
            });
    }

    @Override
    public void fieldsErrorHandler(List<FieldErrorResponse> fieldErrorResponseList) {
        for (FieldErrorResponse fieldErrorResponse : fieldErrorResponseList) {
            switch (fieldErrorResponse.getCode()) {
                case "0":
                    ToastUtils.show(context.getString(R.string.error_vote_0));
                    break;
                case "1":
                    ToastUtils.show(context.getString(R.string.error_vote_1));
                    break;
                case "2":
                    ToastUtils.show(context.getString(R.string.error_vote_2));
                    break;
                case "3":
                    ToastUtils.show(context.getString(R.string.error_vote_3));
                    break;
                case "4":
                    ToastUtils.show(context.getString(R.string.error_vote_4));
                    break;
                case "5":
                    ToastUtils.show(context.getString(R.string.error_vote_5));
                    break;
                case "6":
                    ToastUtils.show(context.getString(R.string.error_vote_6));
                    voteDetail(currentVoteUuid);
                    break;
            }
        }
    }
}
