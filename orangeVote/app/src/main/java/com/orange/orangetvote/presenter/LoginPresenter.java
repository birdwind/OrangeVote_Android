package com.orange.orangetvote.presenter;

import android.content.Context;

import com.orange.orangetvote.basic.base2.BaseObserver;
import com.orange.orangetvote.basic.base2.BasePresenter;
import com.orange.orangetvote.view.LoginView;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(LoginView baseView) {
        super(baseView);
    }

    public void login(String username, String pwd){
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", pwd);
        addDisposable(apiServer.executeFormPost("login", map), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onLoginSucc();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
