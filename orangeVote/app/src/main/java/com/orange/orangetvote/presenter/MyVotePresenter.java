package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.response.voteRecord.MyVoteResponse;
import com.orange.orangetvote.response.voteRecord.MyVoteServerResponse;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.MyVoteView;
import java.util.List;
import okhttp3.internal.http.RealResponseBody;

public class MyVotePresenter extends AbstractPresenter<MyVoteView> {

    public MyVotePresenter(MyVoteView baseView) {
        super(baseView);
    }

    public void myVoteList() {
        initMap();

        addDisposable(apiServer.executeGet(VoteApiServer.MY_VOTE_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<RealResponseBody, MyVoteServerResponse, MyVoteResponse, FieldErrorResponse>(baseView,
                MyVoteServerResponse.class) {
                @Override
                public void onSuccess(List<MyVoteResponse> responseList) {
                    baseView.loadMyVoteList(responseList);
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> fieldErrorResponseList) {

                }
            });
    }

    public void myVotedList() {
        initMap();

        addDisposable(apiServer.executeGet(VoteApiServer.MY_VOTED_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<RealResponseBody, MyVoteServerResponse, MyVoteResponse, FieldErrorResponse>(baseView,
                MyVoteServerResponse.class) {
                @Override
                public void onSuccess(List responseList) {

                }

                @Override
                public void onFieldsError(List responseFieldErrorList) {

                }
            });
    }
}
