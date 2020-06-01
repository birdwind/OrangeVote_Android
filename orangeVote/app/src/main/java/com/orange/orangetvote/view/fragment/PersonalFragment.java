package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.PersonalPresenter;
import com.orange.orangetvote.request.PersonalRequest;
import com.orange.orangetvote.response.personal.PersonalResponse;
import com.orange.orangetvote.view.viewCallback.PersonalView;
import java.util.List;
import android.os.Bundle;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;

public class PersonalFragment extends AbstractFragment<PersonalPresenter> implements PersonalView {

    private static String memberUuid;
    @BindView(R.id.et_personal_orangeid)
    EditText etOrangeId;

    @BindView(R.id.et_personal_username)
    EditText etUsername;

    @BindView(R.id.et_personal_name)
    EditText etName;

    @BindView(R.id.et_personal_nickname)
    EditText etNickname;

    @BindView(R.id.et_personal_school)
    EditText etSchool;

    @BindView(R.id.et_personal_major)
    EditText etMajor;

    @OnClick(R.id.bt_personal_update)
    void clickUpdate() {

        PersonalRequest personalRequest = new PersonalRequest(memberUuid, etUsername.getText().toString(),
            etNickname.getText().toString(), etSchool.getText().toString(), etMajor.getText().toString());
        presenter.updatePersonalInfo(personalRequest);
    }

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
        etOrangeId.setText(personalResponse.getOrangeId());
        etName.setText(personalResponse.getName());
        etUsername.setText(personalResponse.getUsername());
        etNickname.setText(personalResponse.getNickname());
        etMajor.setText(personalResponse.getMajor());
        etSchool.setText(personalResponse.getSchool());
        memberUuid = personalResponse.getMemberUuid();
    }

    @Override
    public void updatePersonalSuccess(List<PersonalResponse> personalResponseList) {
        showToast(getString(R.string.success_personal_update));
        PersonalResponse personalResponse = personalResponseList.get(0);
        etOrangeId.setText(personalResponse.getOrangeId());
        etName.setText(personalResponse.getName());
        etUsername.setText(personalResponse.getUsername());
        etNickname.setText(personalResponse.getNickname());
        etMajor.setText(personalResponse.getMajor());
        etSchool.setText(personalResponse.getSchool());
        memberUuid = personalResponse.getMemberUuid();
    }
}
