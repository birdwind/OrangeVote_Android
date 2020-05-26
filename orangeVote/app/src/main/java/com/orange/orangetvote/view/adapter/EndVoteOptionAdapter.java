package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.customerView.AutoBreakViewGroup;
import com.orange.orangetvote.response.vote.VoteOptionDetailResponse;
import java.util.List;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class EndVoteOptionAdapter extends BaseQuickAdapter<VoteOptionDetailResponse, BaseViewHolder> {

    public EndVoteOptionAdapter(int layoutResId, @Nullable List<VoteOptionDetailResponse> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteOptionDetailResponse item) {
        helper.setText(R.id.tv_option, item.getText());
        helper.setText(R.id.tv_option_percent, item.getPercent() + "%");
        helper.setProgress(R.id.pb_option, item.getPercent());

        if (item.getSelecteds() != null && item.getSelecteds().size() > 0) {
            final AutoBreakViewGroup abvgSelectedTag = helper.getView(R.id.autobreakll_selected_tag);
            for (String selectedsPersonName : item.getSelecteds()) {
                View view = View.inflate(mContext, R.layout.component_vote_option_selected_person_item, null);
                TextView tvSelectedPersonName = view.findViewById(R.id.tv_vote_option_selected_person_name);
                tvSelectedPersonName.setText(selectedsPersonName);
                abvgSelectedTag.addView(view);
            }
        }
    }

}
