package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.response.voteList.VoteOptionResponse;
import com.orange.orangetvote.response.voteList.VoteResponse;
import com.orange.orangetvote.view.adapter.callback.VoteListener;
import java.util.List;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VoteAdapte extends BaseQuickAdapter<VoteResponse, BaseViewHolder> implements
    BaseQuickAdapter.OnItemChildClickListener, View.OnFocusChangeListener{

    private VoteListener voteListener;

    private CheckBox cbAddOption;

    private EditText etAddOption;

    public VoteAdapte(int layoutResId, List<VoteResponse> data, VoteListener voteListener) {
        super(layoutResId, data);
        this.voteListener = voteListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, VoteResponse item) {
        final RecyclerView recyclerView = helper.getView(R.id.rv_vote_option);
        final VoteOptionAdapte voteOptionAdapte =
            new VoteOptionAdapte(R.layout.component_vote_option_item, item.getOption(), item.getValue());
        recyclerView.setLayoutManager(
            new LinearLayoutManager(helper.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        helper.setText(R.id.tv_vote_title, item.getText());
        helper.setText(R.id.tv_vote_description, item.getContent());
        helper.setText(R.id.tv_vote_team, item.getTeam());

        if (item.getIsSign()) {
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

        if (item.getIsAllowAdd()) {
            helper.setGone(R.id.ll_vote_addOption, true);
        } else {
            helper.setGone(R.id.ll_vote_addOption, false);
        }

        recyclerView.setAdapter(voteOptionAdapte);
        voteOptionAdapte.setOnItemChildClickListener(this);
        helper.setTag(R.id.et_vote_addOption, item.getValue());
        cbAddOption = helper.getView(R.id.cb_vote_addOption);
        etAddOption = helper.getView(R.id.et_vote_addOption);
        etAddOption.setOnFocusChangeListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.cb_option) {
            CheckBox checkBox = (CheckBox) view;
            VoteOptionResponse voteOptionResponse = (VoteOptionResponse) adapter.getItem(position);
            if (checkBox.isChecked()) {
                Boolean isCanAdded =
                    voteListener.onClickOption(view.getTag().toString(), voteOptionResponse.getValue());
                if (!isCanAdded) {
                    checkBox.setChecked(false);
                }
            } else {
                checkBox.setChecked(false);
                voteListener.onCancelOption(view.getTag().toString(), voteOptionResponse.getValue());
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view.getId() == R.id.et_vote_addOption) {
            if (b) {
                cbAddOption.setChecked(true);
            } else {
                String option = ((EditText) view).getText().toString().trim();
                if (!option.isEmpty()) {
                    boolean isCanAdded = voteListener.onAddOption(view.getTag().toString(), option);
                    if (!isCanAdded) {
                        cbAddOption.setChecked(false);
                    }
                } else {
                    cbAddOption.setChecked(false);
                }
            }
        }
    }
}
