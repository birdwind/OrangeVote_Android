package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.response.vote.MyVoteResponse;
import com.orange.orangetvote.response.vote.MyVoteServerResponse;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.viewCallback.MyVoteView;
import java.util.List;

public class MyVotePresenter extends AbstractPresenter<MyVoteView> {

    public MyVotePresenter(MyVoteView baseView) {
        super(baseView);
    }

    public void myVoteList() {
        initMap();

        addDisposable(apiServer.executeGet(VoteApiServer.MY_VOTE_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<MyVoteServerResponse, MyVoteResponse>(baseView, MyVoteServerResponse.class) {
                @Override
                public void onSuccess(List<MyVoteResponse> responseList) {
                    baseView.loadMyVoteList(responseList);
                }

                @Override
                public void onResponseError(String responseError) {

                }

                @Override
                public void onResponseFieldError(List<FieldErrorResponse> fieldErrorResponseList) {

                }
            });
    }

    public void myVotedList() {
        initMap();

        addDisposable(apiServer.executeGet(VoteApiServer.MY_VOTED_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<MyVoteServerResponse, MyVoteResponse>(baseView, MyVoteServerResponse.class) {
                @Override
                public void onResponseError(String responseError) {

                }

                @Override
                public void onSuccess(List responseList) {
                    baseView.loadMyVotedList(responseList);
                }

                @Override
                public void onResponseFieldError(List responseFieldErrorList) {

                }
            });
    }

    @Override
    public void fieldsErrorHandler(List<FieldErrorResponse> fieldErrorResponseList) {

    }
}
