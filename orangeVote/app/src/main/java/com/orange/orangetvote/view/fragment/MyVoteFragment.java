package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.MyVotePresenter;
import com.orange.orangetvote.response.voteRecord.MyVoteResponse;
import com.orange.orangetvote.view.callback.MyVoteView;
import java.util.List;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;

public class MyVoteFragment extends AbstractFragment<MyVotePresenter> implements MyVoteView {
    private List<MyVoteResponse> myVoteResponseList;

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
        return R.layout.fragment_my_vote;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        presenter.myVoteList();
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void loadMyVoteList(List<MyVoteResponse> myVoteResponseList) {
        this.myVoteResponseList.clear();
        this.myVoteResponseList.addAll(myVoteResponseList);
    }
}
