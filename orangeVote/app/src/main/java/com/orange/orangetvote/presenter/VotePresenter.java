package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.VoteView;

public class VotePresenter extends BasePresenter<VoteView> {

    public VotePresenter(VoteView baseView) {
        super(baseView);
    }

    public void getList(){
        paramsMap.clear();
        headerMap.clear();

        addDisposable(apiServer.executeGet(VoteApiServer.VOTE_LIST.valueOfName(), paramsMap, headerMap), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onListSucc(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
