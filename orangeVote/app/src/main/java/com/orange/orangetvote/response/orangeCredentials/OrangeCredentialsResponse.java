package com.orange.orangetvote.response.orangeCredentials;

import com.orange.orangetvote.basic.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrangeCredentialsResponse implements BaseResponse {

    private String name;

    private String nickname;

    private String identity;

    private String orangeId;

    private String school;

    private String major;

    private String graduation;
}
