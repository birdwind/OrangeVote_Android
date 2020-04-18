package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.response.voteList.VoteResponse;

import java.util.List;

public class VoteAdapte extends BaseQuickAdapter<VoteResponse.VoteResponseEntity, BaseViewHolder> {

    public VoteAdapte(int layoutResId, List<VoteResponse.VoteResponseEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteResponse.VoteResponseEntity item) {
        helper.setText(R.id.tv_vote_title, item.getText());
    }
}
