package com.orange.orangetvote.view.activity;


import android.widget.EditText;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.BaseActivity;
import com.orange.orangetvote.presenter.LoginPresenter;
import com.orange.orangetvote.view.callback.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    @OnClick(R.id.bt_login)
    void click_btLogin() {
        presenter.login(etUsername.getText().toString(), etPassword.getText().toString());
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLoginSucc() {
        showtoast("登入成功");
        startActivity(MainActivity.class);
    }
}
