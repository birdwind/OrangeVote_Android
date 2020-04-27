package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.request.VoteRequest;
import com.orange.orangetvote.response.vote.VoteServerResponse;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.VoteView;

public class VotePresenter extends BasePresenter<VoteView> {

    public VotePresenter(VoteView baseView) {
        super(baseView);
    }

    public void voteList() {
        initParamAndHeader();

        addDisposable(apiServer.executeGet(VoteApiServer.VOTE_LIST.valueOfName(), paramsMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                public void onSuccess(String responseJson) {
                    VoteServerResponse voteServerResponse =
                        GsonUtils.parseJsonToBean(responseJson, VoteServerResponse.class);
                    baseView.loadVoteListApiSuccess(voteServerResponse.getResponse());
                }

                @Override
                public void onError(String msg) {
                    baseView.showError(msg);
                }

                @Override
                protected void onFieldsError(String responseJson) {

                }
            });
    }

    public void vote(VoteRequest voteRequest) {
        initParamAndHeader();
        initRequestBodyMap();
        paramsMap.put("vote", GsonUtils.toJson(voteRequest));
        packageToRequestBody();
        addDisposable(apiServer.executePostMultipart(VoteApiServer.VOTE.valueOfName(), requestBodyMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                public void onSuccess(String responseJson) {
                    VoteServerResponse voteServerResponse =
                        GsonUtils.parseJsonToBean(responseJson, VoteServerResponse.class);
                    baseView.onVotedApiSuccess();
                }

                @Override
                public void onError(String msg) {

                }

                @Override
                protected void onFieldsError(String responseJson) {

                }
            });

    }
}
