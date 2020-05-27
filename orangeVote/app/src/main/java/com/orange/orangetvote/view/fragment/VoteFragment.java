package com.orange.orangetvote.view.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.model.AddUpdateVoteOptionModel;
import com.orange.orangetvote.presenter.VotePresenter;
import com.orange.orangetvote.request.VoteRequest;
import com.orange.orangetvote.response.vote.VoteResponse;
import com.orange.orangetvote.view.adapter.VoteAdapter;
import com.orange.orangetvote.view.adapter.callback.VoteListener;
import com.orange.orangetvote.view.callback.VoteView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class VoteFragment extends AbstractFragment<VotePresenter>
    implements VoteView, VoteListener, OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    private List<VoteResponse> voteResponseList;

    private VoteAdapter voteAdapter;

    private Map<String, List<String>> optionListMap;

    private Map<String, List<AddUpdateVoteOptionModel>> addOptionModelListMap;

    @BindView(R.id.rv_vote)
    RecyclerView rvVote;

    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    public VotePresenter createPresenter() {
        return new VotePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_vote;
    }

    @Override
    public void addListener() {
        voteAdapter.setOnItemChildClickListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initView() {
        rvVote.setHasFixedSize(true);
        rvVote.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVote.setAdapter(voteAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        voteResponseList = new ArrayList<>();
        initLocalTempData();
        voteAdapter = new VoteAdapter(R.layout.component_vote_item, voteResponseList, this);
    }

    @Override
    public void doSomething() {
        presenter.voteList();
    }

    @Override
    public void loadVoteListApiSuccess(List<VoteResponse> voteResponseList) {
        this.voteResponseList.clear();
        this.voteResponseList.addAll(voteResponseList);
        voteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onVotedApiSuccess() {
        showToast(getString(R.string.vote_success));
        smartRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        initLocalTempData();
        presenter.voteList();
        refreshLayout.finishRefresh();
    }

    private void initLocalTempData() {
        optionListMap = new HashMap<>();
        addOptionModelListMap = new HashMap<>();
    }

    private int getVoteSelectedOption(String voteUuid) {
        List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList = addOptionModelListMap.get(voteUuid);
        List<String> voteList = optionListMap.get(voteUuid);
        if (addUpdateVoteOptionModelList == null) {
            addUpdateVoteOptionModelList = new ArrayList<>();
        }
        if (voteList == null) {
            voteList = new ArrayList<>();
        }
        return addUpdateVoteOptionModelList.size() + voteList.size();
    }

    private int getVoteMultiply(String voteUuid) {
        int multiply = 1;

        for (int i = 0; i < voteResponseList.size(); i++) {
            VoteResponse voteResponse = voteResponseList.get(i);
            if (voteResponse.getVoteUuid().equals(voteUuid)) {
                multiply = voteResponse.getMultiSelection();
            }
        }
        return multiply;
    }

    private void showMultiplyError(String multiply) {
        showToast(getString(R.string.vote_error_multiply).replace("{multiply}", multiply));
    }

    @Override
    public Boolean onClickOption(String voteUuid, String optionUuid) {
        int multiply = getVoteMultiply(voteUuid);
        if (getVoteSelectedOption(voteUuid) < multiply) {
            List<String> optionList = optionListMap.get(voteUuid);
            if (optionList == null) {
                optionList = new ArrayList<>();
                optionList.add(optionUuid);
                optionListMap.put(voteUuid, optionList);
            } else {
                boolean isOptionExist = false;
                for (int i = 0; i < optionList.size(); i++) {
                    if (optionList.get(i).equals(optionUuid)) {
                        isOptionExist = true;
                        break;
                    }
                }
                if (!isOptionExist) {
                    optionList.add(optionUuid);
                }
            }
            return true;
        } else {
            showMultiplyError(String.valueOf(multiply));
            return false;
        }
    }

    @Override
    public void onCancelOption(String voteUuid, String optionUuid) {
        List<String> optionList = optionListMap.get(voteUuid);
        if (optionList != null) {
            optionList.remove(optionUuid);
        }
    }

    @Override
    public Boolean onAddOption(String voteUuid, String option) {
        int multiply = getVoteMultiply(voteUuid);
        AddUpdateVoteOptionModel addUpdateVoteOptionModel = new AddUpdateVoteOptionModel(option);

        if (getVoteSelectedOption(voteUuid) < multiply) {
            List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList = addOptionModelListMap.get(voteUuid);
            if (addUpdateVoteOptionModelList == null) {
                addUpdateVoteOptionModelList = new ArrayList<>();
                addUpdateVoteOptionModelList.add(addUpdateVoteOptionModel);
                addOptionModelListMap.put(voteUuid, addUpdateVoteOptionModelList);
            } else {
                boolean isOptionExist = false;
                for (int i = 0; i < addUpdateVoteOptionModelList.size(); i++) {
                    if (addUpdateVoteOptionModelList.get(i).getValue().equals(option)) {
                        isOptionExist = true;
                        break;
                    }
                }
                if (!isOptionExist) {
                    addUpdateVoteOptionModelList.add(addUpdateVoteOptionModel);
                }
            }
            return true;
        } else {
            showMultiplyError(String.valueOf(multiply));
            return false;
        }
    }

    @Override
    public void onCancelAddOption(String voteUuid, String option) {
        List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList = addOptionModelListMap.get(voteUuid);
        if (addUpdateVoteOptionModelList != null) {
            for (int i = 0; i < addUpdateVoteOptionModelList.size(); i++) {
                if (addUpdateVoteOptionModelList.get(i).getValue().equals(option)) {
                    addUpdateVoteOptionModelList.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.bt_vote) {
            String voteUuid = view.getTag().toString();
            VoteRequest voteRequest =
                new VoteRequest(voteUuid, optionListMap.get(voteUuid), addOptionModelListMap.get(voteUuid));
            presenter.vote(voteRequest);
        }
    }

    @Override
    protected String setTitle() {
        return getString(R.string.navigation_home);
    }

    @Override
    public Boolean isNeedShowBackOnToolBar() {
        return false;
    }
}
