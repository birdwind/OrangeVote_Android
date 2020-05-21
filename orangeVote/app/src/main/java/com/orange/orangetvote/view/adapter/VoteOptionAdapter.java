package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.response.vote.VoteOptionResponse;
import java.util.List;

public class VoteOptionAdapter extends BaseQuickAdapter<VoteOptionResponse, BaseViewHolder> {

    private String voteUuid;

    private boolean isVoted;

    public VoteOptionAdapter(int layoutResId, List<VoteOptionResponse> data, String voteUuid, boolean isVoted) {
        super(layoutResId, data);
        this.voteUuid = voteUuid;
        this.isVoted = isVoted;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteOptionResponse item) {
        helper.setText(R.id.cb_option, item.getText());
        helper.setTag(R.id.cb_option, voteUuid);
        helper.setChecked(R.id.cb_option, item.getSelect());
        if (isVoted) {
            helper.getView(R.id.cb_option).setEnabled(false);
        } else {
            helper.getView(R.id.cb_option).setEnabled(true);
        }
        helper.addOnClickListener(R.id.cb_option);
    }
}
