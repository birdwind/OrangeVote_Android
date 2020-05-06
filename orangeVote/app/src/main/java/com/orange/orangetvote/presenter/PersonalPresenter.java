package com.orange.orangetvote.presenter;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.BaseObserver;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.request.PersonalRequest;
import com.orange.orangetvote.response.personal.PersonalServerResponse;
import com.orange.orangetvote.response.system.ResponseFieldError;
import com.orange.orangetvote.server.PersonalApiServer;
import com.orange.orangetvote.view.callback.PersonalView;
import java.util.List;

public class PersonalPresenter extends BasePresenter<PersonalView> {

    public PersonalPresenter(PersonalView baseView) {
        super(baseView);
    }

    public void loadPersonalInfo() {
        initParamAndHeader();
        addDisposable(apiServer.executeGet(PersonalApiServer.PERSONAL_INFO.valueOfName(), paramsMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                public void onSuccess(String responseJson) {
                    PersonalServerResponse personalServerResponse =
                        GsonUtils.parseJsonToBean(responseJson, PersonalServerResponse.class);
                    baseView.loadPersonalSuccess(personalServerResponse.getResponse());
                }

                @Override
                public void onError(String msg) {
                    baseView.showError(msg);
                }

                @Override
                protected void onFieldsError(String responseJson) {

                }
            });
    }

    public void updatePersonalInfo(PersonalRequest personalRequest) {
        initParamAndHeader();
        paramsMap.put("member", GsonUtils.toJson(personalRequest));
        packageToRequestBody();
        addDisposable(
            apiServer.executePostMultipart(PersonalApiServer.PERSONAL_UPDATE.valueOfName(), requestBodyMap, headerMap),
            new BaseObserver(baseView) {
                @Override
                public void onSuccess(String responseJson) {
                    PersonalServerResponse personalServerResponse =
                        GsonUtils.parseJsonToBean(responseJson, PersonalServerResponse.class);
                    baseView.updatePersonalSuccess(personalServerResponse.getResponse());
                }

                @Override
                public void onError(String msg) {
                    baseView.showError(msg);
                }

                @Override
                protected void onFieldsError(String responseJson) {
                    String[] error = context.getResources().getStringArray(R.array.error_personal);
                    PersonalServerResponse personalServerResponse =
                        GsonUtils.parseJsonToBean(responseJson, PersonalServerResponse.class);
                    List<ResponseFieldError> responseFieldErrorList = personalServerResponse.getResponseFieldError();
                    for (ResponseFieldError responseFieldError : responseFieldErrorList) {
                        try {
                            baseView.showError(error[Integer.parseInt(responseFieldError.getCode())]);
                        } catch (Exception e) {
                            LogUtils.e(e.getMessage());
                        }
                    }
                }
            });
    }

}
