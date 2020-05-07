package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.view.AbstractFragment;
import android.os.Bundle;

public class VoteRecordFragment extends AbstractFragment {
    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_vote_record;
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

    @Override
    protected String setTitle() {
        return getString(R.string.account_vote_record);
    }
}
