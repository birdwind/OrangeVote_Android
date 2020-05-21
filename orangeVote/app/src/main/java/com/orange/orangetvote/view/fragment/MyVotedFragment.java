package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.MyVotePresenter;
import com.orange.orangetvote.response.voteRecord.MyVoteResponse;
import com.orange.orangetvote.view.callback.MyVoteView;
import java.util.List;
import android.os.Bundle;

public class MyVotedFragment extends AbstractFragment<MyVotePresenter> implements MyVoteView {
    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    public MyVotePresenter createPresenter() {
        return new MyVotePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_voted;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        presenter.myVotedList();
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void loadMyVoteList(List<MyVoteResponse> myVoteResponseList) {

    }

    @Override
    public void loadMyVotedList(List<MyVoteResponse> myVoteResponseList) {

    }
}
