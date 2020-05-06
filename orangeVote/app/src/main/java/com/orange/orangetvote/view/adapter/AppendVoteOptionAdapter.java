package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.view.listener.AppendOptionListener;
import java.util.List;
import android.widget.EditText;
import androidx.annotation.Nullable;

public class AppendVoteOptionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private AppendOptionListener appendOptionListener;

    public AppendVoteOptionAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
//        this.appendOptionListener = appendOptionListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.iv_append_vote_delete);
        helper.addOnClickListener(R.id.et_append_vote_option);
//        EditText editText = helper.getView(R.id.et_append_vote_option);

    }
}
