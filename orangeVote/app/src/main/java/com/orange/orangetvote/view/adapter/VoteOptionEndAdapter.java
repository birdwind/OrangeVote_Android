package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.response.vote.VoteOptionResponse;
import com.orange.orangetvote.response.voteRecord.VoteOptionDetailResponse;
import java.util.List;
import android.widget.ProgressBar;

public class VoteOptionEndAdapter extends BaseQuickAdapter<VoteOptionDetailResponse, BaseViewHolder> {

    private String voteUuid;

    public VoteOptionEndAdapter(int layoutResId, List<VoteOptionDetailResponse> data, String voteUuid) {
        super(layoutResId, data);
        this.voteUuid = voteUuid;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteOptionDetailResponse item) {
        helper.setText(R.id.tv_option, item.getText());
        helper.setText(R.id.tv_option_percent, item.getPercent() + "%");
        ProgressBar votePercentProgressBar = helper.getView(R.id.pb_option);
        votePercentProgressBar.setProgress(item.getPercent());
    }
}
