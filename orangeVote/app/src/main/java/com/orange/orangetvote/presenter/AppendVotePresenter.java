package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.server.TeamApiServer;
import com.orange.orangetvote.view.callback.AppendVoteView;
import java.io.IOException;
import okhttp3.ResponseBody;

public class AppendVotePresenter extends BasePresenter<AppendVoteView> {
    public AppendVotePresenter(AppendVoteView baseView) {
        super(baseView);
    }

    public void teamList() {
        initParamAndHeader();
        addDisposable(apiServer.executeGet(TeamApiServer.TEAM_LIST.valueOfName(), paramsMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                protected void onSuccess(String responseJson){

                }

                @Override
                protected void onError(String msg) {

                }

                @Override
                protected void onFieldsError(String responseJson) {

                }

            });
    }
}
