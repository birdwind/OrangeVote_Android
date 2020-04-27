package com.orange.orangetvote.request;

import com.orange.orangetvote.basic.request.AbstractRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalRequest extends AbstractRequest {

    public PersonalRequest(String memberUuid, String username, String nickname, String school, String major) {
        this.memberUuid = memberUuid;
        this.username = username;
        this.nickname = nickname;
        this.school = school;
        this.major = major;
    }

    private String memberUuid;

    private String username;

    private String nickname;

    private String school;

    private String major;
}
