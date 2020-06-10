package com.orange.orangetvote.basic.utils.notification;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.view.activity.BottomNavigationActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils2 notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        LogUtils.d("From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            LogUtils.d("Message data payload: " + remoteMessage.getData());
            int badge = Integer.parseInt(remoteMessage.getData().get("badge"));
            LogUtils.d("notificationNumber", ":" + badge);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            LogUtils.d("Message Notification Body: " + remoteMessage.getNotification().getBody());
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String timestamp = String.valueOf(remoteMessage.getSentTime());
            String imageUrl = remoteMessage.getData().get("image");
            Intent resultIntent = new Intent(Intent.ACTION_VIEW);

            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
            if (imageUrl == null) {
                showNotificationMessage(this, title, message, timestamp, resultIntent);

            } else {
                showNotificationMessageWithBigImage(this, title, message, timestamp, resultIntent, imageUrl);
            }
        }
    }
    // [END receive_message]

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        LogUtils.d("Short lived task is done.");
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp,
        Intent intent) {
        notificationUtils = new NotificationUtils2(context);
        intent = new Intent(context, BottomNavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.clearNotifications(context);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp,
        Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils2(context);
        intent = new Intent(context, BottomNavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.clearNotifications(context);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
