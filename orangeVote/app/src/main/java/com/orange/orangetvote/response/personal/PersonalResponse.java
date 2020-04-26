package com.orange.orangetvote.response.personal;

import com.orange.orangetvote.basic.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalResponse implements BaseResponse {

    private String memberUuid;

    private String name;

    private String username;

    private String nickname;

    private String orangeId;

    private String school;

    private String major;
}
