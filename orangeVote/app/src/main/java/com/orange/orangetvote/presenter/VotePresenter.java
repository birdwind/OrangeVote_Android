package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.request.VoteRequest;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.response.vote.VoteResponse;
import com.orange.orangetvote.response.vote.VoteServerResponse;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.VoteView;
import java.util.List;
import okhttp3.internal.http.RealResponseBody;

public class VotePresenter extends AbstractPresenter<VoteView> {

    public VotePresenter(VoteView baseView) {
        super(baseView);
    }

    public void voteList() {
        initParamAndHeader();

        addDisposable(apiServer.executeGet(VoteApiServer.VOTE_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<RealResponseBody, VoteServerResponse, VoteResponse, FieldErrorResponse>(baseView,
                VoteServerResponse.class) {
                @Override
                public void onSuccess(List<VoteResponse> responseList) {
                    baseView.loadVoteListApiSuccess(responseList);
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> fieldErrorResponseList) {

                }
            });
    }

    public void vote(VoteRequest voteRequest) {
        initParamAndHeader();
        initRequestBodyMap();
        paramsMap.put("vote", GsonUtils.toJson(voteRequest));
        packageToRequestBody();
        addDisposable(apiServer.executePostMultipart(VoteApiServer.VOTE.valueOfName(), requestBodyMap, headerMap),
            new AbstractObserver<RealResponseBody, VoteServerResponse, VoteResponse, FieldErrorResponse>(baseView,
                VoteServerResponse.class) {

                @Override
                public void onSuccess(List<VoteResponse> responseList) {
                    baseView.onVotedApiSuccess();
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> fieldErrorResponseList) {

                }
            });

    }
}
