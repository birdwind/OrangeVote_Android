package com.orange.orangetvote.view.dialog;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.ToastUtils;
import com.orange.orangetvote.basic.view.AbstractDialog;
import com.orange.orangetvote.view.dialog.callback.AddTeamDialogListener;
import android.content.Context;
import android.widget.EditText;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class AddTeamDialog extends AbstractDialog<AddTeamDialogListener> {

    @BindView(R.id.et_dialog_add_team_pass_code)
    EditText etPassCode;

    @OnClick(R.id.bt_dialog_add_team_confirm)
    void clickBTConfirm() {
        String passCode = etPassCode.getText().toString().trim();
        if (passCode.isEmpty()) {
            ToastUtils.show(context.getString(R.string.add_team_passcode_hint));
        } else {
            dialogListener.addTeamByPassCode(etPassCode.getText().toString());
            etPassCode.setText("");
            dismiss();
        }
    }

    public AddTeamDialog(@NonNull Context context, AddTeamDialogListener callback) {
        super(context, callback);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_add_team;
    }

    @Override
    public void doSomething() {

    }
}
