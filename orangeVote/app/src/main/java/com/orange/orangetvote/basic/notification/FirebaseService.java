package com.orange.orangetvote.basic.notification;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orange.orangetvote.basic.config.Config;
import com.orange.orangetvote.basic.utils.GsonUtils;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.NotificationUtils;
import com.orange.orangetvote.view.activity.Notification;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class FirebaseService extends FirebaseMessagingService {

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        LogUtils.e("From : " + remoteMessage.getFrom());
        if (remoteMessage == null)
            return;
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            LogUtils.e("推播內容 : " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
            // handleDataMessage(remoteMessage.getData().toString());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            LogUtils.e("推播data payload : " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                handleDataMessage(json);
            } catch (Exception e) {
                LogUtils.e("Exception: " + e.getMessage());
            }
        }

        // LogUtils.e("推播標題 : " + remoteMessage.getNotification().getTitle());
        // sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        LogUtils.e("Firebase Token : " + s);
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {

        NotificationObject notificationObject = GsonUtils.parseJsonToBean(json.toString(), NotificationObject.class);
        LogUtils.e("JSON : " + json.toString());

        LogUtils.e("title: " + notificationObject.getTitle());
        LogUtils.e("message : " + notificationObject.getMessage());
        LogUtils.e("payload : " + notificationObject.getPayload());
        LogUtils.e("imageUrl : " + notificationObject.getImageUrl());
        LogUtils.e("timestamp: " + notificationObject.getTimestamp());

        Intent resultIntent = new Intent(getApplicationContext(), Notification.class);
        resultIntent.putExtra("title", notificationObject.getTitle());
        resultIntent.putExtra("message", notificationObject.getMessage());

        if (TextUtils.isEmpty(notificationObject.getImageUrl())) {
            showNotificationMessage(getApplicationContext(), notificationObject.getTitle(),
                notificationObject.getMessage(), notificationObject.getTimestamp(), resultIntent,
                notificationObject.getChannelID());
        } else {
            // image is present, show notification with image
            showNotificationMessageWithBigImage(getApplicationContext(), notificationObject.getTitle(),
                notificationObject.getMessage(), notificationObject.getTimestamp(), resultIntent,
                notificationObject.getImageUrl(), notificationObject.getChannelID());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent,
        int channelID) {
        notificationUtils = new NotificationUtils(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, channelID);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp,
        Intent intent, String imageUrl, int channelID) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl, channelID);
    }
}
