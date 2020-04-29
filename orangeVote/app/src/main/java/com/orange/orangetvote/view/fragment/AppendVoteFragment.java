package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.AppendVotePresenter;
import com.orange.orangetvote.view.callback.AppendVoteView;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AppendVoteFragment extends AbstractFragment<AppendVotePresenter> implements AppendVoteView {


    @Override
    public AppendVotePresenter createPresenter() {
        return new AppendVotePresenter(this);
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
        presenter.teamList();
    }

    @Override
    public void doSomething() {

    }

    @Override
    protected String setTitle() {
        return getString(R.string.navigation_add);
    }

    @Override
    public Boolean isNeedShowBackOnToolBar() {
        return false;
    }

    @Override
    public void loadTeamListSuccess() {

    }
}
