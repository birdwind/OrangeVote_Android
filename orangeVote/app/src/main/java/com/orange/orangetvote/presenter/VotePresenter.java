package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.rxHelper.RxException;
import com.orange.orangetvote.response.voteList.VoteListServerResponse;
import com.orange.orangetvote.response.voteList.VoteResponse;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.VoteView;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public class VotePresenter extends BasePresenter<VoteView> {

    public VotePresenter(VoteView baseView) {
        super(baseView);
    }

    public void getList() {
        paramsMap.clear();
        headerMap.clear();

        addDisposable(apiServer.executeGet(VoteApiServer.VOTE_LIST.valueOfName(), paramsMap, headerMap), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                VoteListServerResponse voteListServerResponse = null;
                VoteResponse voteResponse = null;
                try {
                    String responseBody = ((ResponseBody) o).string();
                    voteListServerResponse = GsonUtils.parseJsonToBean(responseBody, VoteListServerResponse.class);
                    if(voteListServerResponse.getErrorCode() == 2){
                        baseView.onLoginError();
                        return;
                    }else{
                        voteResponse = GsonUtils.parseJsonToBean(responseBody, VoteResponse.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.d(RxException.handleException(e).getMessage());
                }

                baseView.onListSucc(voteResponse);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
