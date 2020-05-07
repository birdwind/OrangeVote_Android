package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.view.dialog.LogoutDialog;
import com.orange.orangetvote.view.dialog.LogoutDialogListener;
import android.os.Bundle;
import butterknife.OnClick;

public class AccountFragment extends AbstractFragment implements LogoutDialogListener {

    private LogoutDialog logoutDialog;

    @OnClick(R.id.tv_account_personal)
    void clickPersonal() {
        fragmentNavigationListener.pushFragment(new PersonalFragment());
    }

    @OnClick(R.id.tv_account_vote_record)
    void clickVoteRecord() {
        fragmentNavigationListener.pushFragment(new VoteRecordFragment());
    }

    @OnClick(R.id.tv_account_logout)
    void clickLogout() {
        logoutDialog.show();
    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {
        logoutDialog =
            new LogoutDialog(context, this, getString(R.string.logout_title), getString(R.string.logout_content));
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {

    }

    @Override
    protected String setTitle() {
        return getString(R.string.navigation_account);
    }

    @Override
    public Boolean isNeedShowBackOnToolBar() {
        return false;
    }

    @Override
    public void confirmLogout() {
        logout();
    }
}
