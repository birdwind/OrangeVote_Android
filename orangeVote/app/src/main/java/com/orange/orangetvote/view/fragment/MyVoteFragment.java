package com.orange.orangetvote.view.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.MyVotePresenter;
import com.orange.orangetvote.response.vote.MyVoteResponse;
import com.orange.orangetvote.view.adapter.MyVoteAdapter;
import com.orange.orangetvote.view.callback.MyVoteView;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MyVoteFragment extends AbstractFragment<MyVotePresenter> implements MyVoteView, BaseQuickAdapter.OnItemClickListener {
    private List<MyVoteResponse> myVoteResponseList;

    private MyVoteAdapter myVoteAdapter;

    @BindView(R.id.rv_my_vote)
    RecyclerView rvVote;

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
        myVoteAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initView() {
        rvVote.setHasFixedSize(true);
        rvVote.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVote.setAdapter(myVoteAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        presenter.myVoteList();
        myVoteResponseList = new ArrayList<>();
        myVoteAdapter = new MyVoteAdapter(myVoteResponseList);
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void loadMyVoteList(List<MyVoteResponse> myVoteResponseList) {
        this.myVoteResponseList.clear();
        this.myVoteResponseList.addAll(myVoteResponseList);
        myVoteAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMyVotedList(List<MyVoteResponse> myVoteResponseList) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MyVoteResponse myVoteResponse = (MyVoteResponse) adapter.getItem(position);

        VoteDetailFragment voteDetailFragment = new VoteDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("voteUuid", myVoteResponse.getValue());
        voteDetailFragment.setArguments(bundle);
        fragmentNavigationListener.pushFragment(voteDetailFragment);
    }
}
