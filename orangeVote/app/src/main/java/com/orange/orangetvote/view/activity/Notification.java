package com.orange.orangetvote.view.activity;

import com.orange.orangetvote.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Notification extends AppCompatActivity {

    @BindView(R.id.tv_notification_title)
    TextView tvTitle;

    @BindView(R.id.tv_notification_message)
    TextView tvMessage;

    protected Unbinder unbinder;

    private String title;

    private String message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.notification);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        parseDataToView(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseDataToView(intent);
    }

    private void parseDataToView(Intent intent){
        title = intent.getStringExtra("title");
        message = intent.getStringExtra("message");

        tvTitle.setText(title);
        tvMessage.setText(message);
    }
}
