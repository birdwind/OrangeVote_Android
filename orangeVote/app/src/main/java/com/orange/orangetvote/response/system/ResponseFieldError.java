package com.orange.orangetvote.response.system;

import com.orange.orangetvote.basic.response.AbstractResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseFieldError extends AbstractResponse {

    private String rejectedValue;

    private String defaultMessage;

    private String code;

    private String field;
}
