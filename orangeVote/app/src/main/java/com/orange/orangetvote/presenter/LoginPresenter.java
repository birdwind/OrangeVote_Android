package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.config.Config;
import com.orange.orangetvote.basic.utils.SharedPreferencesUtils;
import com.orange.orangetvote.response.login.LoginResponse;
import com.orange.orangetvote.response.login.LoginServerResponse;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.server.AuthApiServer;
import com.orange.orangetvote.view.viewCallback.LoginView;
import java.util.List;

public class LoginPresenter extends AbstractPresenter<LoginView> {

    public LoginPresenter(LoginView baseView) {
        super(baseView);
    }

    public void login(String username, String pwd) {
        initMap();

        fieldMap.put("username", username);
        fieldMap.put("password", pwd);

        removeCookie();

        addDisposable(
            apiServer.executePostFormUrlEncode(AuthApiServer.Login.valueOfName(), paramsMap, fieldMap, headerMap),
            new AbstractObserver<LoginServerResponse, LoginResponse>(baseView, LoginServerResponse.class) {

                @Override
                public void onSuccess(List<LoginResponse> responseList) {
                    SharedPreferencesUtils.put(Config.COOKIES, retrofitManager.getCookies().toString());
                    baseView.onLoginSucc();
                }

                @Override
                public void onResponseError(String responseError) {

                }

                @Override
                public void onResponseFieldError(List<FieldErrorResponse> fieldErrorResponseList) {

                }
            });
    }

    @Override
    public void fieldsErrorHandler(List<FieldErrorResponse> fieldErrorResponseList) {

    }
}
