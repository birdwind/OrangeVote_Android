package com.orange.orangetvote;

import com.google.firebase.iid.FirebaseInstanceId;
import com.orange.orangetvote.basic.utils.LogUtils;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class MainApplication extends Application {

    private static MainApplication mainApplication;

    public static Context getAppContext() {
        return mainApplication;
    }

    public static Resources getAppResources() {
        return mainApplication.getResources();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            if (task.getResult() == null)
                return;
            String token = task.getResult().getToken();
            LogUtils.e("FirebaseToken :" + token);
        });
    }
}
