package com.orange.orangetvote.view.activity;

import com.jaeger.library.StatusBarUtil;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractActivity;
import com.orange.orangetvote.enums.IdentityEnum;
import com.orange.orangetvote.presenter.OrangeCredentialsPresenter;
import com.orange.orangetvote.response.orangeCredentials.OrangeCredentialsResponse;
import com.orange.orangetvote.view.viewCallback.OrangeCredentialsView;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;

public class OrangeCredentialsActivity extends AbstractActivity<OrangeCredentialsPresenter>
    implements OrangeCredentialsView {

    @BindView(R.id.tv_orange_credentials_identity)
    TextView tvIdentity;

    @BindView(R.id.tv_orange_credentials_name)
    TextView tvName;

    @BindView(R.id.tv_orange_credentials_nickname)
    TextView tvNickname;

    @BindView(R.id.tv_orange_credentials_id)
    TextView tvOrangeId;

    @BindView(R.id.tv_orange_credentials_school)
    TextView tvSchool;

    @BindView(R.id.tv_orange_credentials_major)
    TextView tvMajor;

    @BindView(R.id.tv_orange_credentials_graduation)
    TextView tvGraduation;

    @Override
    protected void onBackPress() {

    }

    @Override
    public OrangeCredentialsPresenter createPresenter() {
        return new OrangeCredentialsPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_orange_credentials;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {
        StatusBarUtil.setTransparent(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        presenter.loadOrangeCredentials();
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void onLoadOrangeCredentials(OrangeCredentialsResponse orangeCredentialsResponse) {
        String identity =
            getString((Integer) IdentityEnum.getEnumByName(orangeCredentialsResponse.getIdentity()).valueOf());
        String name = orangeCredentialsResponse.getName();
        String nickname = orangeCredentialsResponse.getNickname();
        String graduation = orangeCredentialsResponse.getGraduation();
        String major = orangeCredentialsResponse.getMajor();
        String orangeId = orangeCredentialsResponse.getOrangeId();
        String school = orangeCredentialsResponse.getSchool();
        tvIdentity.setText(identity);
        tvName.setText(name);
        tvNickname.setText(nickname);
        tvSchool.setText(school);
        tvMajor.setText(major);
        tvOrangeId.setText(orangeId);
        tvGraduation.setText(graduation);
    }
}
