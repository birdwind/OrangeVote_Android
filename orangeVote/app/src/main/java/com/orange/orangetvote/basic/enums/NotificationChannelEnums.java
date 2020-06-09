package com.orange.orangetvote.basic.enums;

import java.io.Serializable;

public enum NotificationChannelEnums implements BaseEnums {
    DEFAULT(0, "default_channel");

    private int value;

    private String name;

    NotificationChannelEnums(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Serializable valueOf() {
        return value;
    }

    @Override
    public String valueOfName() {
        return name;
    }

    public static String getValueOfName(int value) {
        for (NotificationChannelEnums notificationChannelEnum : NotificationChannelEnums.values()) {
            if (value == ((int) notificationChannelEnum.valueOf())) {
                return notificationChannelEnum.valueOfName();
            }
        }
        return DEFAULT.valueOfName();
    }
}
