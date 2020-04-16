package com.orange.orangetvote.view.activity;

import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.BaseActivity;
import com.orange.orangetvote.basic.utils.ToastUtils;
import com.orange.orangetvote.contract.VoteContract;
import com.orange.orangetvote.model.VoteModel;
import com.orange.orangetvote.presenter.VotePresenter;

import butterknife.BindView;

public class VoteActivity extends BaseActivity<VotePresenter> implements VoteContract.ContractView {


//    @BindView(R.id.tv_text) TextView tvText;

    @Override
    protected void initView() {
        mPresenter.contractPresent();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void createPresenter() {
        mPresenter = new VotePresenter(mContext, this, new VoteModel());
    }

    @Override
    public void viewSuccess(Object entity) {
        ToastUtils.show("成功");
//        tvText.setText("成功");
    }

    @Override
    public void viewError(String reason) {
        ToastUtils.show("失敗");
//        tvText.setText("失敗");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
