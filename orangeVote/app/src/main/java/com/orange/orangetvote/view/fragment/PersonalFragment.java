package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.PersonalPresenter;
import com.orange.orangetvote.response.personal.PersonalResponse;
import com.orange.orangetvote.view.callback.PersonalView;
import java.util.List;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;

public class PersonalFragment extends AbstractFragment<PersonalPresenter> implements PersonalView {

    @BindView(R.id.tv_personal_orangeid)
    TextView tvOrangeId;

    @BindView(R.id.tv_personal_username)
    TextView tvUsername;

    @BindView(R.id.tv_personal_name)
    TextView tvName;

    @BindView(R.id.et_personal_nickname)
    EditText etNickname;

    @BindView(R.id.et_personal_school)
    EditText etSchool;

    @BindView(R.id.et_personal_major)
    EditText etMajor;

    @Override
    public PersonalPresenter createPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        presenter.loadPersonalInfo();
    }

    @Override
    public void doSomething() {

    }

    @Override
    protected String setTitle() {
        return getString(R.string.account_personal);
    }

    @Override
    public void loadPersonalSuccess(List<PersonalResponse> personalResponseList) {
        PersonalResponse personalResponse = personalResponseList.get(0);
        tvOrangeId.setText(personalResponse.getOrangeId());
        tvName.setText(personalResponse.getName());
        tvUsername.setText(personalResponse.getUsername());
        etNickname.setText(personalResponse.getNickname());
        etMajor.setText(personalResponse.getMajor());
        etSchool.setText(personalResponse.getSchool());
    }
}
