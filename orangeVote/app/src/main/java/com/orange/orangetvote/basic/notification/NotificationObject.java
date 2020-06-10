package com.orange.orangetvote.basic.notification;

import com.orange.orangetvote.basic.enums.NotificationChannelEnums;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationObject implements Serializable {

    private String title;

    private String message;

    private String image;

    private int imageType;

    private int channelID = (int) NotificationChannelEnums.DEFAULT.valueOf();
}

