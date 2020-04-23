package com.orange.orangetvote.view.activity;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractActivity;
import com.orange.orangetvote.presenter.LoginPresenter;
import com.orange.orangetvote.view.callback.LoginView;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends AbstractActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_password)
    EditText etPassword;

    @OnClick(R.id.bt_login)
    void clickBtLogin() {
        presenter.login(etUsername.getText().toString(), etPassword.getText().toString());
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void doSomething() {

    }

    @Override
    public void onLoginSucc() {
        showToast("登入成功");
        startActivity(BottomNavigationActivity.class);
    }
}
