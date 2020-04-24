package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.request.VoteRequest;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.VoteView;
import com.orange.orangetvote.response.vote.VoteServerResponse;
import java.io.IOException;
import okhttp3.ResponseBody;

public class VotePresenter extends BasePresenter<VoteView> {

    public VotePresenter(VoteView baseView) {
        super(baseView);
    }

    public void voteList() {
        initParamAndHeader();

        addDisposable(apiServer.executeGet(VoteApiServer.VOTE_LIST.valueOfName(), paramsMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                public void onSuccess(ResponseBody responseBody) throws IOException {
                    VoteServerResponse voteServerResponse =
                        GsonUtils.parseJsonToBean(responseBody.string(), VoteServerResponse.class);
                    if (voteServerResponse.getErrorCode() == 2) {
                        baseView.onLoginError();
                        return;
                    } else {
                        baseView.loadVoteListApiSuccess(voteServerResponse.getResponse());
                    }
                }

                @Override
                public void onError(String msg) {
                    baseView.showError(msg);
                }
            });
    }

    public void vote(VoteRequest voteRequest) {
        initParamAndHeader();
        paramsMap.put("vote", GsonUtils.toJson(voteRequest));
        packageToRequestBody();
        addDisposable(apiServer.executePostMultipart(VoteApiServer.VOTE.valueOfName(), requestBodyMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                public void onSuccess(ResponseBody responseBody) throws IOException {
                    VoteServerResponse voteServerResponse =
                        GsonUtils.parseJsonToBean(responseBody.string(), VoteServerResponse.class);
                    switch (voteServerResponse.getErrorCode()) {
                        case 0:
                            baseView.onVotedApiSuccess();
                            break;
                        case 2:
                            baseView.onLoginError();
                            break;
                        case 6:
                            LogUtils.e(voteServerResponse.getResponseFieldError().get(0).getCode());
                            break;
                    }
                }

                @Override
                public void onError(String msg) {

                }
            });

    }
}
