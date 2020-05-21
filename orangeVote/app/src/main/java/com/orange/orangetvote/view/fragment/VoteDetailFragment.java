package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.view.AbstractFragment;
import android.os.Bundle;

public class VoteDetailFragment extends AbstractFragment {
    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_append_vote;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        LogUtils.e(getArguments().getString("voteUuid"));
    }

    @Override
    public void doSomething() {

    }
}
