package com.orange.orangetvote.view.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.view.AbstractActivity;
import com.orange.orangetvote.presenter.VotePresenter;
import com.orange.orangetvote.basic.utils.fragmentNavUtils.FragNavController;
import com.orange.orangetvote.basic.utils.fragmentNavUtils.FragmentHistory;
import com.orange.orangetvote.basic.utils.fragmentNavUtils.FragmentNavigationListener;
import com.orange.orangetvote.view.fragment.AccountFragment;
import com.orange.orangetvote.view.fragment.AppendVoteFragment;
import com.orange.orangetvote.view.fragment.VoteFragment;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindArray;
import butterknife.BindView;

public class BottomNavigationActivity extends AbstractActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentNavigationListener,
    FragNavController.RootFragmentListener, FragNavController.TransactionListener {

    private List<Fragment> fragments;

    private FragNavController mNavController;

    private FragmentHistory fragmentHistory;

    @BindArray(R.array.tab_name)
    String[] TABS;

    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_container)
    FrameLayout frameLayout;

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
    public void initView() {}

    @Override
    public void initData(Bundle savedInstanceState) {
        fragments = new ArrayList<>(3);
        fragments.add(new VoteFragment());
        fragments.add(new AppendVoteFragment());
        fragments.add(new AccountFragment());

        fragmentHistory = new FragmentHistory();

        mNavController =
            FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_container)
                .transactionListener(this).rootFragmentListener(this, TABS.length).build();
    }

    @Override
    public void doSomething() {
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         resetDefaultIcon();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.i_home:
                item.setIcon(R.drawable.icon_home_selected);
                switchTab(0);
                fragmentHistory.push(0);
                return true;
            case R.id.i_add:
                switchTab(1);
                fragmentHistory.push(1);
                return true;
            case R.id.i_account:
                switchTab(2);
                fragmentHistory.push(2);
                return true;
        }

        fragmentHistory.push(item.getItemId());
        return true;
    }

    private void resetDefaultIcon() {
        Menu menu = bottomNavigationViewEx.getMenu();
        menu.findItem(R.id.i_home).setIcon(R.drawable.icon_home);
    }

    @Override
    protected void onBackPress() {

    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            if (fragmentHistory.isEmpty()) {
                super.onBackPressed();
            } else {
                if (fragmentHistory.getStackSize() > 1) {

                    int position = fragmentHistory.popPrevious();

                    switchTab(position);

                } else {
                    switchTab(0);

                    fragmentHistory.emptyStack();
                }
            }
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FragNavController.TAB1:
                return new VoteFragment();
            case FragNavController.TAB2:
                return new AppendVoteFragment();
            case FragNavController.TAB3:
                return new AccountFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        if (getSupportActionBar() != null && mNavController != null) {
            updateToolbar();
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        if (getSupportActionBar() != null && mNavController != null) {
            updateToolbar();
        }
    }

    private void updateToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setDisplayShowHomeEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    private void switchTab(int position) {
        mNavController.switchTab(position);
        bottomNavigationViewEx.setSelectedItemId(position);
    }
}
