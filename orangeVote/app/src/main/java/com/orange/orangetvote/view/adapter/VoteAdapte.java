package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.entity.VoteResponseEntity;

import java.util.List;

public class VoteAdapte extends BaseQuickAdapter<VoteResponseEntity, BaseViewHolder> {

    public VoteAdapte(int layoutResId, List<VoteResponseEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteResponseEntity item) {
        helper.setText(R.id.tv_vote_title, item.getText());
    }
}
