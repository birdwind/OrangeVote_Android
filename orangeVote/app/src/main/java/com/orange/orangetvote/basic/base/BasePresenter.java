package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.response.system.FieldErrorResponse;
import java.util.List;

public interface BasePresenter {
    void fieldsErrorHandler(List<FieldErrorResponse> fieldErrorResponseList);
}
