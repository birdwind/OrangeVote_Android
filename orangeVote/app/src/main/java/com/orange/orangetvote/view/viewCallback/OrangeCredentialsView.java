package com.orange.orangetvote.view.viewCallback;

import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.orangeCredentials.OrangeCredentialsResponse;

public interface OrangeCredentialsView extends BaseView {
    void onLoadOrangeCredentials(OrangeCredentialsResponse orangeCredentialsResponse);
}
