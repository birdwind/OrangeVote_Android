package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.view.dialog.AddTeamDialog;
import com.orange.orangetvote.view.dialog.callback.AddTeamDialogListener;
import android.os.Bundle;
import butterknife.OnClick;

public class AuthorityFragment extends AbstractFragment implements AddTeamDialogListener {

    private AddTeamDialog addTeamDialog;

    @OnClick(R.id.tv_authority_add_team)
    void clickTVAddTeam() {
        addTeamDialog.show();
    }

    @Override
    protected String setTitle() {
        return getString(R.string.account_authority);
    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_authority;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {}

    @Override
    public void initData(Bundle savedInstanceState) {
        addTeamDialog = new AddTeamDialog(context, this);
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void addTeamByPassCode(String passCode) {
        // TODO: 加入團隊
    }
}
