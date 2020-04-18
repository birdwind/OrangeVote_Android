package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.network.RetrofitManager;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.SharedPreferencesUtils;
import com.orange.orangetvote.response.login.LoginResponse;
import com.orange.orangetvote.response.login.LoginServerResponse;
import com.orange.orangetvote.view.callback.LoginView;

import java.io.IOException;

import okhttp3.ResponseBody;

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

        addDisposable(apiServer.executeFormPost("login", paramsMap, headerMap), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                LoginServerResponse loginServerResponse = null;
                LoginResponse loginResponse = null;
                try {
                    String responseBody = ((ResponseBody) o).string();
                    loginServerResponse = GsonUtils.parseJsonToBean(responseBody, LoginServerResponse.class);
                    LogUtils.print(responseBody);
                    switch (loginServerResponse.getErrorCode()){
                        case 0:
                            loginResponse = GsonUtils.parseJsonToBean(responseBody, LoginResponse.class);
                            break;
                        case 9:
                            onError("帳號或密碼錯誤");
                            return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SharedPreferencesUtils.put("JSESSIONID", RetrofitManager.getInstance().getCookies().toString());

                baseView.onLoginSucc();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
