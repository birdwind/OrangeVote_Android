package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.basic.response.AbstractResponse;
import com.orange.orangetvote.basic.response.BaseResponse;
import okhttp3.ResponseBody;

public abstract class AbstractObserver<RS extends AbstractResponse, RD extends BaseResponse>
    extends AbstractBaseObserver<ResponseBody, RS, RD> {
    public AbstractObserver(BaseView view, Class<RS> clazz) {
        super(view, clazz);
    }
}
