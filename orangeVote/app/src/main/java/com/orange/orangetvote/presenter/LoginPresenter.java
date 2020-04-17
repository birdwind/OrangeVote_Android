package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.response.login.LoginResponseEntity;
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
                ResponseBody responseBody = (ResponseBody) o;
                LoginResponseEntity loginResponseEntity = null;
                try {
                    loginResponseEntity = GsonUtils.parseJsonToBean(responseBody.string(), LoginResponseEntity.class);
                    System.out.println(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(loginResponseEntity.getResponse());

                baseView.onLoginSucc();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
