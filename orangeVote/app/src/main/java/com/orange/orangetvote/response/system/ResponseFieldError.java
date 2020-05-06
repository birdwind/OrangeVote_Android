package com.orange.orangetvote.response.system;

import com.orange.orangetvote.basic.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseFieldError implements BaseResponse {

    private String rejectedValue;

    private String defaultMessage;

    private String code;

    private String field;
}
