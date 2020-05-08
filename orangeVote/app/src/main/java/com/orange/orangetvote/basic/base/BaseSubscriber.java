package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.basic.response.BaseResponse;
import com.orange.orangetvote.response.system.FieldErrorResponse;
import java.util.List;

public interface BaseSubscriber<RD extends BaseResponse, FE extends FieldErrorResponse>{
    void onSuccess(List<RD> responseList);

    void onError(String msg);

    void onFieldsError(List<FE> responseFieldErrorList);
}
