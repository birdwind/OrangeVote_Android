package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.UpdateVotePresenter;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import com.orange.orangetvote.view.callback.UpdateVoteView;
import com.skydoves.powerspinner.PowerSpinnerView;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import butterknife.BindView;

public class VoteDetailFragment extends AbstractFragment<UpdateVotePresenter> implements UpdateVoteView {

    private String voteUuid;

    private List<String> teamValueList;
    private List<String> teamUuidList;

    @BindView(R.id.psv_append_vote_team)
    PowerSpinnerView psvTeam;

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    public UpdateVotePresenter createPresenter() {
        return new UpdateVotePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_append_vote;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        teamValueList = new ArrayList<>();
        teamUuidList = new ArrayList<>();
        if (getArguments() != null) {
            voteUuid = getArguments().getString("voteUuid");
        }
        presenter.teamList();
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void loadTeamListSuccess(List<TeamListResponse> teamListResponseList) {
        teamValueList.clear();
        teamUuidList.clear();
        for (TeamListResponse teamListResponse : teamListResponseList) {
            teamValueList.add(teamListResponse.getTeamValue());
            teamUuidList.add(teamListResponse.getTeamUuid());
        }
        psvTeam.setItems(teamValueList);
    }
}
