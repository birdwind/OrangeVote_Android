package com.orange.orangetvote.presenter;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.request.PersonalRequest;
import com.orange.orangetvote.response.personal.PersonalResponse;
import com.orange.orangetvote.response.personal.PersonalServerResponse;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.server.PersonalApiServer;
import com.orange.orangetvote.view.callback.PersonalView;
import java.util.List;
import okhttp3.ResponseBody;

public class PersonalPresenter extends AbstractPresenter<PersonalView> {

    public PersonalPresenter(PersonalView baseView) {
        super(baseView);
    }

    public void loadPersonalInfo() {
        initParamAndHeader();
        addDisposable(apiServer.executeGet(PersonalApiServer.PERSONAL_INFO.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<ResponseBody, PersonalServerResponse, PersonalResponse, FieldErrorResponse>(baseView,
                PersonalServerResponse.class) {
                @Override
                public void onSuccess(List<PersonalResponse> responseList) {
                    baseView.loadPersonalSuccess(responseList);
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> fieldErrorResponseList) {

                }
            });
    }

    public void updatePersonalInfo(PersonalRequest personalRequest) {
        initParamAndHeader();
        packageToParamsMap(personalRequest);

        addDisposable(
            apiServer.executePostFormUrlEncode(PersonalApiServer.PERSONAL_UPDATE.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<ResponseBody, PersonalServerResponse, PersonalResponse, FieldErrorResponse>(baseView,
                PersonalServerResponse.class) {

                @Override
                public void onSuccess(List<PersonalResponse> responseList) {
                    baseView.updatePersonalSuccess(responseList);
                }

                @Override
                public void onFieldsError(List<FieldErrorResponse> fieldErrorResponseList) {
                    String[] error = context.getResources().getStringArray(R.array.error_personal);
                    for (FieldErrorResponse fieldErrorResponse : fieldErrorResponseList) {
                        baseView.showError(error[Integer.parseInt(fieldErrorResponse.getCode())]);
                    }
                }
            });
    }

}
