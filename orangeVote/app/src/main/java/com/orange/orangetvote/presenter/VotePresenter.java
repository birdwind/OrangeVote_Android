package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseModel;
import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.rxHelper.RxException;
import com.orange.orangetvote.entity.VoteOptionResponseEntity;
import com.orange.orangetvote.entity.VoteResponseEntity;
import com.orange.orangetvote.server.VoteApiServer;
import com.orange.orangetvote.view.callback.VoteView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                BaseModel baseEntity = null;
                List<VoteResponseEntity> voteResponseEntities = new ArrayList<>();
                try {
                    baseEntity = GsonUtils.parseJsonToBean(responseBody.string(), BaseModel.class);
                    System.out.println(responseBody.string());

                    JSONArray voteArray = new JSONArray(GsonUtils.toJson(baseEntity.getResponse()));
                    for (int i = 0; i < voteArray.length(); i++) {
                        JSONObject voteObject = voteArray.getJSONObject(i);
                        JSONArray voteOptionArray = voteObject.getJSONArray("option");
                        List<VoteOptionResponseEntity> voteOptionResponseEntities = new ArrayList<>();
                        for (int optionIndex = 0; optionIndex < voteOptionArray.length(); optionIndex++) {
                            JSONObject voteOptionObject = voteOptionArray.getJSONObject(optionIndex);
                            voteOptionResponseEntities.add(new VoteOptionResponseEntity(
                                    voteOptionObject.getBoolean("select"),
                                    voteOptionObject.getString("text"),
                                    voteOptionObject.getString("value")));
                        }
                        voteResponseEntities.add(new VoteResponseEntity(
                                voteObject.getString("text"),
                                voteObject.getString("value"),
                                voteObject.getString("content"),
                                voteObject.getInt("multiSelection"),
                                voteObject.getBoolean("isAllowAdd"),
                                voteObject.getBoolean("isSign"),
                                voteOptionResponseEntities
                        ));
                    }
                } catch (Exception e) {
                    LogUtils.d(RxException.handleException(e).getMessage());
                }

                baseView.onListSucc(voteResponseEntities);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
