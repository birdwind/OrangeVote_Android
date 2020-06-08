package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.basic.response.AbstractResponse;
import com.orange.orangetvote.basic.response.BaseResponse;
import okhttp3.ResponseBody;

public abstract class AbstractSubscriber<RS extends AbstractResponse, RD extends BaseResponse>
    extends AbstractBaseSubscriber<ResponseBody, RS, RD> {
    public AbstractSubscriber(BaseView view, Class<RS> clazz) {
        super(view, clazz);
    }
}
