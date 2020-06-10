package com.orange.orangetvote.basic.notification;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.NotificationUtils;
import com.orange.orangetvote.view.activity.BottomNavigationActivity;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

public class FCMService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        LogUtils.d("FCM From", remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            LogUtils.d("FCM Notification Body", remoteMessage.getNotification().getBody());
            LogUtils.d("FCM data", remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody(), remoteMessage.getData(),
                String.valueOf(remoteMessage.getSentTime()));
        }
    }

    private void handleNotification(String notificationBody, Map<String, String> dataMap, String sendTime) {
        NotificationObject notificationObject = GsonUtils.parseJsonToBean(notificationBody, NotificationObject.class);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        Intent intent = new Intent(Intent.ACTION_VIEW);

        String title = null;
        String message = null;
        String image = null;
        int imageType = 0;
        int channelID = 0;

        if (notificationObject != null) {
            title = notificationObject.getTitle();
            message = notificationObject.getMessage();
            image = notificationObject.getImage();
            imageType = notificationObject.getImageType();
            channelID = notificationObject.getChannelID();

            notificationUtils.playNotificationSound();
        }

        if (dataMap.get("image") == null) {
            showNotificationMessage(this, title, message, sendTime, intent, channelID);
        } else {
            showNotificationMessage(this, title, message, image, imageType, sendTime, intent, channelID);
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent,
        int channelID) {
        showNotificationMessage(context, title, message, null, 0, timeStamp, intent, channelID);
    }

    private void showNotificationMessage(Context context, String title, String message, String image, int imageType,
        String timeStamp, Intent intent, int channelID) {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        intent.setClass(context, BottomNavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationUtils.showNotificationMessage(title, message, image, timeStamp, intent, channelID);

    }
}
