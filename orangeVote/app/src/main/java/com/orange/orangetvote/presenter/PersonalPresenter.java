package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.response.personal.PersonalServerResponse;
import com.orange.orangetvote.server.PersonalApiServer;
import com.orange.orangetvote.view.callback.PersonalView;
import java.io.IOException;
import okhttp3.ResponseBody;

public class PersonalPresenter extends BasePresenter<PersonalView> {

    public PersonalPresenter(PersonalView baseView) {
        super(baseView);
    }

    public void loadPersonalInfo() {
        initParamAndHeader();
        addDisposable(apiServer.executeGet(PersonalApiServer.PERSONAL_INFO.valueOfName(), paramsMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                public void onSuccess(ResponseBody responseBody) throws IOException {
                    PersonalServerResponse personalServerResponse =
                        GsonUtils.parseJsonToBean(responseBody.string(), PersonalServerResponse.class);
                    switch (personalServerResponse.getErrorCode()) {
                        case 0:
                            baseView.loadPersonalSuccess(personalServerResponse.getResponse());
                            break;
                        case 2:
                            baseView.onLoginError();
                            break;
                    }
                }

                @Override
                public void onError(String msg) {
                    baseView.showError(msg);
                }
            });
    }

}
