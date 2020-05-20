package com.orange.orangetvote.view.fragment;

import com.google.android.material.tabs.TabLayout;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.view.adapter.ViewPageAdapter;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class VoteRecordFragment extends AbstractFragment {

    @BindView(R.id.tablayout_vote_record)
    TabLayout voteRecord;

    @BindView(R.id.viewpager_vote_record)
    ViewPager viewPager;

    private List<Fragment> fragmentList;

    private List<String> titleList;

    private ViewPageAdapter viewPageAdapter;

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
        viewPager.setAdapter(viewPageAdapter);
        voteRecord.setupWithViewPager(viewPager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        fragmentList.add(new MyVoteFragment());
        fragmentList.add(new MyVotedFragment());
        titleList.add(getString(R.string.vote_record_my_vote));
        titleList.add(getString(R.string.vote_record_my_voted));

        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), 0, fragmentList, titleList);
    }

    @Override
    public void doSomething() {

    }

    @Override
    protected String setTitle() {
        return getString(R.string.account_vote_record);
    }
}
