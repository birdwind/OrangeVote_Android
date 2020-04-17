package com.orange.orangetvote.view.activity;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.BaseActivity;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.presenter.VotePresenter;
import com.orange.orangetvote.view.callback.VoteView;

public class MainActivity extends BaseActivity<VotePresenter> implements VoteView {

//    @BindView(R.id.rv_vote)
//    RecyclerView rvVote;

    @Override
    protected VotePresenter createPresenter() {
        return new VotePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {
        presenter.getList();
    }

    @Override
    public void onListSucc(Object o) {
        LogUtils.print(o.toString());
    }
}
