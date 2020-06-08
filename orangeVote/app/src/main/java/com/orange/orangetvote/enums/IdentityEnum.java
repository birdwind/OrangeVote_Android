package com.orange.orangetvote.enums;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.enums.BaseEnums;
import java.io.Serializable;

public enum IdentityEnum implements BaseEnums {
    ADMIN(R.string.orange_identity_admin, "ADMIN"), CAPTAIN(R.string.orange_identity_captain, "CAPTAIN"), CAPTAIN_2(
        R.string.orange_identity_captain2, "CAPTAIN_2"), MEMBER_1(R.string.orange_identity_member1,
            "MEMBER_1"), MEMBER_2(R.string.orange_identity_member2, "MEMBER_2"), MEMBER_3(
                R.string.orange_identity_member3, "MEMBER_3"), MEMBER_4(R.string.orange_identity_member4,
                    "MEMBER_4"), MEMBER_OLD(R.string.orange_identity_member_old, "MEMBER_OLD");

    private int value;

    private String name;

    IdentityEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Serializable valueOf() {
        return this.value;
    }

    @Override
    public String valueOfName() {
        return this.name;
    }

    public static IdentityEnum getEnumByName(String key) {
        for (IdentityEnum identityEnum : IdentityEnum.values()) {
            if (key.equals(identityEnum.valueOfName())) {
                return identityEnum;
            }
        }
        return null;
    }
}
