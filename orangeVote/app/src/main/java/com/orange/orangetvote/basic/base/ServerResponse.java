package com.orange.orangetvote.basic.base;

import com.orange.orangetvote.basic.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerResponse implements BaseResponse {

    private boolean status;

    private int errorCode;

    private String errorMsg;

    private int httpStatus;

    private String requestUrl;

    private String method;

    private String resourceName;

    private String timeStamp;
}
