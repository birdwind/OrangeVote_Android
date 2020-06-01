package com.orange.orangetvote.view.dialog;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.ToastUtils;
import com.orange.orangetvote.basic.view.AbstractDialog;
import com.orange.orangetvote.view.dialog.callback.AddTeamDialogListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import java.util.List;
import android.content.Context;
import android.widget.EditText;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class AddTeamDialog extends AbstractDialog<AddTeamDialogListener> {

    private List<String> teamValueList;

    @BindView(R.id.psv_dialog_add_team_team)
    PowerSpinnerView psvTeam;

    @BindView(R.id.et_dialog_add_team_pass_code)
    EditText etPassCode;

    @OnClick(R.id.bt_dialog_add_team_confirm)
    void clickBTConfirm() {
        String passCode = etPassCode.getText().toString().trim();
        if (passCode.isEmpty()) {
            ToastUtils.show(context.getString(R.string.add_team_passcode_hint));
        } else {
             dialogListener.addTeamByPassCode(psvTeam.getSelectedIndex(), etPassCode.getText().toString());
            etPassCode.setText("");
        }
    }

    public AddTeamDialog(@NonNull Context context, AddTeamDialogListener callback, List<String> teamValueList) {
        super(context, callback);
        setCanceledOnTouchOutside(true);
        this.teamValueList = teamValueList;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_add_team;
    }

    @Override
    public void doSomething() {
        psvTeam.setItems(teamValueList);
    }
}
