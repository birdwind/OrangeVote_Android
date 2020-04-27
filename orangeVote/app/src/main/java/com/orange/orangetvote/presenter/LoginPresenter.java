package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.config.Config;
import com.orange.orangetvote.basic.network.RetrofitManager;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.SharedPreferencesUtils;
import com.orange.orangetvote.response.login.LoginServerResponse;
import com.orange.orangetvote.server.AuthApiServer;
import com.orange.orangetvote.view.callback.LoginView;

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(LoginView baseView) {
        super(baseView);
    }

    public void login(String username, String pwd) {
        paramsMap.clear();
        paramsMap.put("username", username);
        paramsMap.put("password", pwd);

        headerMap.clear();

        removeCookie();

        addDisposable(apiServer.executePostFormUrlEncode(AuthApiServer.Login.valueOfName(), paramsMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                protected void onSuccess(String responseJson) {
                    LoginServerResponse loginServerResponse =
                        GsonUtils.parseJsonToBean(responseJson, LoginServerResponse.class);
                    SharedPreferencesUtils.put(Config.COOKIES, RetrofitManager.getInstance().getCookies().toString());
                    baseView.onLoginSucc();
                }

                @Override
                protected void onError(String msg) {
                    baseView.showError(msg);

                }

                @Override
                protected void onFieldsError(String responseJson) {

                }
            });
    }
}
