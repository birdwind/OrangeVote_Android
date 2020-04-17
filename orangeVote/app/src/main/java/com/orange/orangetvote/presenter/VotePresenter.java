package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.rxHelper.RxException;
import com.orange.orangetvote.response.voteList.VoteListResponseEntity;
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
                ResponseBody responseBody = (ResponseBody) o;
                VoteListResponseEntity voteListResponseEntity = null;
                try {
                    voteListResponseEntity = GsonUtils.parseJsonToBean(responseBody.string(), VoteListResponseEntity.class);
                } catch (Exception e) {
                    LogUtils.d(RxException.handleException(e).getMessage());
                }

                baseView.onListSucc(new ArrayList<>(voteListResponseEntity.getResponse()));
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
