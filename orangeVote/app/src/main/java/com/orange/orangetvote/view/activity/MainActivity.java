package com.orange.orangetvote.view.activity;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.BaseActivity;
import com.orange.orangetvote.entity.VoteResponseEntity;
import com.orange.orangetvote.presenter.VotePresenter;
import com.orange.orangetvote.view.adapter.VoteAdapte;
import com.orange.orangetvote.view.callback.VoteView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<VotePresenter> implements VoteView, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_vote)
    RecyclerView rvVote;

    private VoteAdapte voteAdapte;

    private List<VoteResponseEntity> voteResponseEntities;

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
        voteResponseEntities = new ArrayList<>();
        voteAdapte = new VoteAdapte(R.layout.component_vote_item, voteResponseEntities);
        rvVote.setHasFixedSize(true);
        rvVote.setLayoutManager(new LinearLayoutManager(context));
        voteAdapte.setOnItemChildClickListener(this);
        rvVote.setAdapter(voteAdapte);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onListSucc(List<VoteResponseEntity> o) {
        voteResponseEntities.addAll(o);
        voteAdapte.notifyDataSetChanged();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
