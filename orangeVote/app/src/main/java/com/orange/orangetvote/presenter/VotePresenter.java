package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.response.voteList.VoteServerResponse;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.VoteView;
import java.io.IOException;
import okhttp3.ResponseBody;

public class VotePresenter extends BasePresenter<VoteView> {

    public VotePresenter(VoteView baseView) {
        super(baseView);
    }

    public void voteList() {
        paramsMap.clear();
        headerMap.clear();

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
                        baseView.onListSucc(voteServerResponse.getResponse());
                    }
                }

                @Override
                public void onError(String msg) {
                    baseView.showError(msg);
                }
            });
    }

    public void vote(){
        paramsMap.clear();
        headerMap.clear();
        //TODO: 執行投票http請求
//        addDisposable(apiServer.executePost());

    }
}
