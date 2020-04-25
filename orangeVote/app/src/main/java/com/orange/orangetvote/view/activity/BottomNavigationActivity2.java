package com.orange.orangetvote.view.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractActivity;
import com.orange.orangetvote.presenter.VotePresenter;
import com.orange.orangetvote.view.adapter.ViewPageAdapter;
import com.orange.orangetvote.view.fragment.AccountFragment;
import com.orange.orangetvote.view.fragment.AppendVoteFragment;
import com.orange.orangetvote.view.fragment.VoteFragment;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class BottomNavigationActivity2 extends AbstractActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPageAdapter viewPageAdapter;

    private List<Fragment> fragments;

    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.viewpager_bottomnavigation)
    ViewPager viewpager;

    @BindView(R.id.ll_topbar_close)
    LinearLayout llClose;

    @BindView(R.id.ll_topbar_back)
    LinearLayout llBack;

    @BindView(R.id.ll_topbar_menu)
    LinearLayout llMenu;

    @BindView(R.id.tv_topbar_title)
    TextView tvTitle;

    @BindView(R.id.main_container)
    FrameLayout frameLayout;

    @OnClick(R.id.ll_topbar_back)
    void clickLlBack() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            showBackButton(false);
        }
        onBackPressed();
    }

    @Override
    public VotePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bottom_navigation;
    }

    @Override
    public void addListener() {
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initView() {
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments);
        viewpager.setAdapter(viewPageAdapter);

        bottomNavigationViewEx.setupWithViewPager(viewpager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        fragments = new ArrayList<>(3);
        fragments.add(new VoteFragment());
        fragments.add(new AppendVoteFragment());
        fragments.add(new AccountFragment());
    }

    @Override
    public void doSomething() {

    }

    public void showBackButton(boolean isShow) {
        if (isShow) {
            llBack.setVisibility(View.VISIBLE);
        } else {
            llBack.setVisibility(View.GONE);
        }
    }

    public void showMenuButton(boolean isShow) {
        if (isShow) {
            llMenu.setVisibility(View.VISIBLE);
        } else {
            llMenu.setVisibility(View.GONE);
        }
    }

    public void showCloseButton(boolean isShow) {
        if (isShow) {
            llClose.setVisibility(View.VISIBLE);
        } else {
            llClose.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        resetDefaultIcon();
        switch (item.getItemId()) {
            case R.id.i_home:
                item.setIcon(R.drawable.icon_home_selected);
                break;
        }
        popBackAllFragment();
        return true;
    }

    private void resetDefaultIcon() {
        Menu menu = bottomNavigationViewEx.getMenu();
        menu.findItem(R.id.i_home).setIcon(R.drawable.icon_home);
    }

    @Override
    protected void onBackPress() {

    }
}
