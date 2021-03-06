package com.orange.orangetvote.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.SharedPreferencesUtils;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private String JSESSIONID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                JSESSIONID = SharedPreferencesUtils.get("JSESSIONID", "");
                LogUtils.print(JSESSIONID);
                Intent intent;
                if(JSESSIONID == null){
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }else{
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
