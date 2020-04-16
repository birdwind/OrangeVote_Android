package com.orange.orangetvote.basic.utils;

import android.content.Context;
import android.widget.Toast;

import com.orange.orangetvote.MainApplication;

import androidx.annotation.StringRes;

public class ToastUtils {

    private static Context context = MainApplication.getAppContext();

    private static Toast toast;

    public static void show(@StringRes int resId){
        show(context.getResources().getString(resId));
    }

    public static void show(CharSequence text){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
