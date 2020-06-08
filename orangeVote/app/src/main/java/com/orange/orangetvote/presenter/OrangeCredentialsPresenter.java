package com.orange.orangetvote.presenter;

import com.orange.orangetvote.basic.base.AbstractObserver;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.response.orangeCredentials.OrangeCredentialsResponse;
import com.orange.orangetvote.response.orangeCredentials.OrangeCredentialsServerResponse;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import com.orange.orangetvote.server.PersonalApiServer;
import com.orange.orangetvote.view.viewCallback.OrangeCredentialsView;
import java.util.List;

public class OrangeCredentialsPresenter extends AbstractPresenter<OrangeCredentialsView> {

    public OrangeCredentialsPresenter(OrangeCredentialsView baseView) {
        super(baseView);
    }

    @Override
    public void fieldsErrorHandler(List<FieldErrorResponse> fieldErrorResponseList) {

    }

    public void loadOrangeCredentials() {
        initMap();
        addDisposable(apiServer.executeGet(PersonalApiServer.ORANGE_CREDENTIALS.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<OrangeCredentialsServerResponse, OrangeCredentialsResponse>(baseView,
                OrangeCredentialsServerResponse.class) {

                @Override
                public void onSuccess(List<OrangeCredentialsResponse> responseList) {
                    baseView.onLoadOrangeCredentials(responseList.get(0));
                }

                @Override
                public void onResponseError(String responseError) {

                }

                @Override
                public void onResponseFieldError(List<FieldErrorResponse> responseFieldErrorList) {

                }
            });
    }
}
