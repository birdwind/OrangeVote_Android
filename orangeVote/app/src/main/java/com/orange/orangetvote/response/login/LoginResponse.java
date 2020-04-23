package com.orange.orangetvote.response.login;

import com.orange.orangetvote.basic.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse implements BaseResponse {

    private String orangeId;

    private String username;

    private boolean status;
}
