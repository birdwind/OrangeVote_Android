package com.orange.orangetvote.presenter;

import android.content.Context;

import com.orange.orangetvote.basic.base.BaseContract;
import com.orange.orangetvote.basic.base.BaseBean;
import com.orange.orangetvote.basic.base.BasePresent;
import com.orange.orangetvote.basic.utils.rxHelper.RxObservable;
import com.orange.orangetvote.contract.VoteContract;
import com.orange.orangetvote.model.VoteModel;

public class VotePresenter extends BasePresent<VoteContract.ContractView, VoteModel> implements VoteContract.ContractPresent {


    public VotePresenter(Context mContext, BaseContract.ContractView mView, VoteModel mModel) {
        super(mContext, mView, mModel);
    }

    @Override
    public void contractPresent() {
        mView.showLoading();
        mModel.mVote(new RxObservable() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                mView.hideLoading();
                mView.viewSuccess(baseBean);
            }

            @Override
            public void onFail(String reason) {
                mView.hideLoading();
                mView.viewError(reason);
            }
        });

    }
}
