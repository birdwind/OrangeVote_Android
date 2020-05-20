package com.orange.orangetvote.view.activity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.fragmentNavUtils.FragNavController;
import com.orange.orangetvote.basic.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.orange.orangetvote.basic.utils.fragmentNavUtils.FragmentHistory;
import com.orange.orangetvote.basic.utils.fragmentNavUtils.FragmentNavigationListener;
import com.orange.orangetvote.basic.view.AbstractActivity;
import com.orange.orangetvote.presenter.VotePresenter;
import com.orange.orangetvote.view.fragment.AccountFragment;
import com.orange.orangetvote.view.fragment.AppendVoteFragment;
import com.orange.orangetvote.view.fragment.VoteFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;

public class BottomNavigationActivity extends AbstractActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener, FragmentNavigationListener,
    FragNavController.RootFragmentListener, FragNavController.TransactionListener {

    private FragNavController mNavController;

    private FragmentHistory fragmentHistory;

    private FragNavTransactionOptions popFragNavTransactionOptions;

    private FragNavTransactionOptions tabToRightFragNavTransactionOptions;

    private FragNavTransactionOptions tabToLeftFragNavTransactionOptions;

    private int currentNavigationPosition;

    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx bottomNavigationViewEx;

    @BindView(R.id.ll_topbar_close)
    LinearLayout llClose;

    @BindView(R.id.ll_topbar_menu)
    LinearLayout llMenu;

    @BindView(R.id.ll_topbar_back)
    LinearLayout llBack;

    @BindView(R.id.tv_topbar_title)
    TextView tvTitle;

    @BindView(R.id.main_container)
    FrameLayout frameLayout;

    @OnClick(R.id.ll_topbar_back)
    void clickBack() {
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
    public void initView() {}

    @Override
    public void initData(Bundle savedInstanceState) {
        currentNavigationPosition = 0;

        FragNavTransactionOptions defaultFragNavTransactionOptions = FragNavTransactionOptions.newBuilder().customAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        tabToRightFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).build();;

        tabToLeftFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        fragmentHistory = new FragmentHistory();

        mNavController = FragNavController
            .newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_container).transactionListener(this)
            .rootFragmentListener(this, 3).defaultTransactionOptions(defaultFragNavTransactionOptions).build();

    }

    @Override
    public void doSomething() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        resetDefaultIcon();

        switch (item.getItemId()) {
            case android.R.id.home:
                mNavController.popFragment();
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

    @Override
    protected void onBackPress() {

    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment(popFragNavTransactionOptions);
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
        resetDefaultIcon();
        Menu menu = bottomNavigationViewEx.getMenu();

        // 依靠tab煥頁
        switch (index) {
            case 0:
                menu.getItem(0).setIcon(R.drawable.icon_home_selected);
                break;
            case 1:
            case 2:
        }

        if (getSupportActionBar() != null && mNavController != null) {
        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        // fragment煥頁
        if (getSupportActionBar() != null && mNavController != null) {
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void updateToolbar(String title, Boolean isShowBack, Boolean isShowClose, Boolean isShowMenu) {
        if(title != null){
            tvTitle.setText(title);
        }
        showBackButton(isShowBack);
        showCloseButton(isShowClose);
        showMenuButton(isShowMenu);
    }

    private void showBackButton(boolean isShow) {
        if (isShow) {
            llBack.setVisibility(View.VISIBLE);
        } else {
            llBack.setVisibility(View.GONE);
        }
    }

    private void showMenuButton(boolean isShow) {
        if (isShow) {
            llMenu.setVisibility(View.VISIBLE);
        } else {
            llMenu.setVisibility(View.GONE);
        }
    }

    private void showCloseButton(boolean isShow) {
        if (isShow) {
            llClose.setVisibility(View.VISIBLE);
        } else {
            llClose.setVisibility(View.GONE);
        }
    }

    private void resetDefaultIcon() {
        Menu menu = bottomNavigationViewEx.getMenu();
        menu.findItem(R.id.i_home).setIcon(R.drawable.icon_home);
    }

    private void switchTab(int position) {
        if (position > currentNavigationPosition) {
            mNavController.switchTab(position, tabToRightFragNavTransactionOptions);
        } else if (position < currentNavigationPosition) {
            mNavController.switchTab(position, tabToLeftFragNavTransactionOptions);
        }
        currentNavigationPosition = position;
        bottomNavigationViewEx.setSelectedItemId(position);
    }
}
