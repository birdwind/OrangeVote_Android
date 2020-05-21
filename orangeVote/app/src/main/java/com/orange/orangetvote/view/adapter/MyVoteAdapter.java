package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.response.voteRecord.MyVoteResponse;
import java.util.List;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyVoteAdapter extends BaseMultiItemQuickAdapter<MyVoteResponse, BaseViewHolder>
    implements BaseQuickAdapter.OnItemChildClickListener, View.OnFocusChangeListener {

    private CheckBox cbAddOption;

    private EditText etAddOption;

    /*
     * 0 = 尚未投票 1 = 已投票 2 = 已結束
     */
    public MyVoteAdapter(List<MyVoteResponse> data) {
        super(data);
        addItemType(0, R.layout.component_vote_item);
        addItemType(1, R.layout.component_voted_item);
        addItemType(2, R.layout.component_vote_end_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyVoteResponse item) {
        final RecyclerView recyclerView = helper.getView(R.id.rv_vote_option);
        recyclerView.setLayoutManager(
            new LinearLayoutManager(helper.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        if (item.getItemType() == 0) {
            // 尚未投票
            final VoteOptionAdapter voteOptionAdapter = new VoteOptionAdapter(R.layout.component_vote_option_item,
                item.getOptions(), item.getValue(), item.isVoted());
            helper.setText(R.id.tv_vote_title, item.getText());
            helper.setText(R.id.tv_vote_description, item.getContent());
            helper.setText(R.id.tv_vote_team, item.getTeam());
            initTag(helper, item);

            voteOptionAdapter.setOnItemChildClickListener(this);
            cbAddOption = helper.getView(R.id.cb_vote_addOption);
            etAddOption = helper.getView(R.id.et_vote_addOption);
            etAddOption.setOnFocusChangeListener(this);

            recyclerView.setAdapter(voteOptionAdapter);

            helper.setTag(R.id.et_vote_addOption, item.getValue());
            helper.setTag(R.id.bt_vote, item.getValue());

            helper.addOnClickListener(R.id.bt_vote);

        } else if (item.getItemType() == 1) {
            // 已投票
            final VoteOptionAdapter voteOptionAdapter = new VoteOptionAdapter(R.layout.component_vote_option_item,
                item.getOptions(), item.getValue(), item.isVoted());
            helper.setText(R.id.tv_vote_title, item.getText());
            helper.setText(R.id.tv_vote_description, item.getContent());
            helper.setText(R.id.tv_vote_team, item.getTeam());
            initTag(helper, item);

            recyclerView.setAdapter(voteOptionAdapter);

        } else if (item.getItemType() == 2) {
            // 投票結束
            final VoteOptionEndAdapter voteOptionEndAdapter = new VoteOptionEndAdapter(
                R.layout.component_voted_option_item, item.getOptionsDetail(), item.getValue());
            recyclerView.setAdapter(voteOptionEndAdapter);

            helper.setText(R.id.tv_vote_title, item.getText());
            helper.setText(R.id.tv_vote_description, item.getContent());
            helper.setText(R.id.tv_vote_team, item.getTeam());
            initTag(helper, item);
        }
    }

    private void initTag(BaseViewHolder helper, MyVoteResponse item) {
        if (item.isSign()) {
            helper.setGone(R.id.tv_vote_sign, true);
        } else {
            helper.setGone(R.id.tv_vote_sign, false);
        }

        if (item.getMultiSelection() != 1) {
            helper.setGone(R.id.ll_vote_multiply, true);
            helper.setText(R.id.tv_vote_multiply, String.valueOf(item.getMultiSelection()));
        } else {
            helper.setGone(R.id.ll_vote_multiply, false);
        }

        if (item.getItemType() == 0) {
            if (item.isAllowAdd()) {
                helper.setGone(R.id.ll_vote_addOption, true);
            } else {
                helper.setGone(R.id.ll_vote_addOption, false);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
