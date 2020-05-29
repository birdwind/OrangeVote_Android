package com.orange.orangetvote.view.dialog;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractDialog;
import com.orange.orangetvote.view.dialog.callback.LogoutDialogListener;
import android.content.Context;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class LogoutDialog extends AbstractDialog<LogoutDialogListener> {

    private String title;

    private String content;

    @BindView(R.id.tv_dialog_common_title)
    TextView tvTitle;

    @BindView(R.id.tv_dialog_common_content)
    TextView tvContent;

    @OnClick(R.id.bt_dialog_common_cancel)
    void clickCancel(){
        dismiss();
    }

    @OnClick(R.id.bt_dialog_common_confirm)
    void clickConfirm(){
        dialogListener.confirmLogout();
        dismiss();
    }

    public LogoutDialog(@NonNull Context context, LogoutDialogListener callback, String title, String content) {
        super(context, callback);
        this.title = title;
        this.content = content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_common;
    }

    @Override
    public void doSomething() {
        tvTitle.setText(title);
        tvContent.setText(content);
    }
}
