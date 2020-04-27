package com.orange.orangetvote.basic.response;

import com.orange.orangetvote.response.system.ResponseFieldError;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractResponse<R extends BaseResponse> implements BaseResponse {

//    private boolean status;
//
//    private int errorCode;
//
//    private String errorMsg;
//
//    private int httpStatus;
//
//    private String requestUrl;
//
//    private String method;
//
//    private String resourceName;
//
//    private String timeStamp;

    private List<R> response;

    private String responseError;

    private List<ResponseFieldError> responseFieldError;

}
