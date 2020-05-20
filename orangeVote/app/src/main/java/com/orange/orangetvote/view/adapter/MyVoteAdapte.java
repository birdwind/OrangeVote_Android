package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.response.voteRecord.MyVoteResponse;
import java.util.List;

public class MyVoteAdapte extends BaseMultiItemQuickAdapter<MyVoteResponse, BaseViewHolder> {

    /*
     * 0 = 尚未投票 1 = 已投票 2 = 已結束
     */
    public MyVoteAdapte(List<MyVoteResponse> data) {
        super(data);
        addItemType(0, R.layout.component_vote_item);
        addItemType(1, R.layout.component_voted_item);
        addItemType(2, R.layout.component_vote_end_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyVoteResponse item) {
        if (item.getItemType() == 0) {

        } else if (item.getItemType() == 1) {

        } else if (item.getItemType() == 2) {
        }
    }
}
