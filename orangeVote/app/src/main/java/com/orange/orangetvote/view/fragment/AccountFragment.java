package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.view.AbstractFragment;
import android.os.Bundle;
import butterknife.OnClick;

public class AccountFragment extends AbstractFragment {

    @OnClick(R.id.tv_account_vote_record)
    void clickVoteRecord(){
        fragmentNavigationListener.pushFragment(new VoteRecordFragment());
    }

    @Override
    public BasePresenter createPresenter() {
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

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {

    }
}
