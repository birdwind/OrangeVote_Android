package com.orange.orangetvote.basic.utils.notification;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import androidx.core.app.NotificationCompat;
import me.leolin.shortcutbadger.ShortcutBadger;

public class NotificationUtils2 {

    private Context mContext;

    private static int pendingNotificationsCount = 0;

    private int badgeCount = 0;

    public NotificationUtils2(Context mContext) {
        this.mContext = mContext;
    }

    public static int getPendingNotificationsCount() {
        LogUtils.d("輸出: " + pendingNotificationsCount);
        return pendingNotificationsCount;
    }

    public static void setPendingNotificationsCount(int pendingNotifications) {
        pendingNotificationsCount = pendingNotifications;
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        setPendingNotificationsCount(0);
        // ShortcutBadger.removeCount(context);
        NotificationManager notificationManager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss", Locale.TAIWAN);
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void showNotificationMessage(String title, String message, String timeStamp, Intent intent) {
        showNotificationMessage(title, message, timeStamp, intent, null);
    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent,
        String imageUrl) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        // notification icon
        final int icon = R.mipmap.ic_launcher;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
            PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "");

        final Uri alarmSound = Uri
            .parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/raw/notification");

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    showBigNotification(bitmap, mBuilder, icon, title, message, timeStamp, resultPendingIntent,
                        alarmSound);
                } else {
                    showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                }
            }
        } else {
            showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
            // playNotificationSound();
        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message,
        String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        int color = mContext.getResources().getColor(R.color.colorTheme);
        int pendingNotificationsCount = getPendingNotificationsCount() + 1;
        LogUtils.d("showSmallNotification: " + badgeCount);
        // this.badgeCount+=1;
        int badgeCount = 1;
        // ShortcutBadger.applyCount(mContext, badgeCount); //for 1.1.4+
        LogUtils.d("showSmallNotification: " + badgeCount);
        setPendingNotificationsCount(pendingNotificationsCount);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);
        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setAutoCancel(true).setContentTitle(title)
            .setContentIntent(resultPendingIntent).setSound(alarmSound).setStyle(inboxStyle)
            .setWhen(System.currentTimeMillis()).setShowWhen(true).setSmallIcon(icon).setColor(color)
            .setContentText(message).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).build();
        mBuilder.setVibrate(new long[] {100, 200, 300, 400, 500, 400, 300, 200, 100});
        notification.number = pendingNotificationsCount;
        LogUtils.d("showSmallNotification: " + badgeCount);
//        ShortcutBadger.applyCount(mContext, badgeCount); // for 1.1.4+
        LogUtils.d("showSmallNotification: " + badgeCount);
        NotificationManager notificationManager =
            (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(pendingNotificationsCount, notification);
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title,
        String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        int color = mContext.getResources().getColor(R.color.colorTheme);
        int pendingNotificationsCount = getPendingNotificationsCount() + 1;
        LogUtils.d("showSmallNotification: " + badgeCount);
        // this.badgeCount+=1;
        int badgeCount = 1;
//        ShortcutBadger.applyCount(mContext, badgeCount); // for 1.1.4+
        LogUtils.d("showSmallNotification: " + badgeCount);
        setPendingNotificationsCount(pendingNotificationsCount);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setAutoCancel(true)

            .setContentTitle(title).setContentIntent(resultPendingIntent).setSound(alarmSound).setStyle(bigPictureStyle)
            .setWhen(System.currentTimeMillis()).setShowWhen(true).setSmallIcon(icon).setColor(color)
            .setContentText(message).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setNumber(pendingNotificationsCount).build();
        mBuilder.setVibrate(new long[] {100, 200, 300, 400, 500, 400, 300, 200, 100});
        notification.number = pendingNotificationsCount;
        LogUtils.d("showSmallNotification: " + badgeCount);
        NotificationManager notificationManager =
            (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(pendingNotificationsCount, notification);
    }

    /**
     * Downloading push notification image before displaying it in the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
