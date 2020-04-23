package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.response.voteList.VoteOptionResponse;
import java.util.List;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class VoteOptionAdapte extends BaseQuickAdapter<VoteOptionResponse, BaseViewHolder>{

    private String voteUuid;

    public VoteOptionAdapte(int layoutResId, List<VoteOptionResponse> data, String voteUuid) {
        super(layoutResId, data);
        this.voteUuid = voteUuid;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteOptionResponse item) {
        helper.setText(R.id.cb_option, item.getText());
        helper.setTag(R.id.cb_option, voteUuid);
        helper.addOnClickListener(R.id.cb_option);
    }
}
