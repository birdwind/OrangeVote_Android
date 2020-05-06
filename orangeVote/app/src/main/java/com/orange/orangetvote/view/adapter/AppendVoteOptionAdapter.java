package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import androidx.annotation.Nullable;

public class AppendVoteOptionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AppendVoteOptionAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
