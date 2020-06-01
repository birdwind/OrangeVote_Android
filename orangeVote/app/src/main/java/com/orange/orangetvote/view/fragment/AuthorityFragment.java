package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.model.TeamModel;
import com.orange.orangetvote.presenter.JoinTeamViewPresenter;
import com.orange.orangetvote.response.team.TeamResponse;
import com.orange.orangetvote.view.dialog.AddTeamDialog;
import com.orange.orangetvote.view.dialog.callback.AddTeamDialogListener;
import com.orange.orangetvote.view.viewCallback.JoinTeamView;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import butterknife.OnClick;

public class AuthorityFragment extends AbstractFragment<JoinTeamViewPresenter>
    implements JoinTeamView, AddTeamDialogListener {

    private AddTeamDialog addTeamDialog;

    private List<String> teamValueList;

    private List<TeamModel> teamModelList;

    @OnClick(R.id.tv_authority_add_team)
    void clickTVAddTeam() {
        addTeamDialog.show();
    }

    @Override
    protected String setTitle() {
        return getString(R.string.account_authority);
    }

    @Override
    public JoinTeamViewPresenter createPresenter() {
        return new JoinTeamViewPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_authority;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initView() {}

    @Override
    public void initData(Bundle savedInstanceState) {
        teamModelList = new ArrayList<>();
        teamValueList = new ArrayList<>();
        addTeamDialog = new AddTeamDialog(context, this, teamValueList);
        presenter.loadTeam();
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void addTeamByPassCode(int selectedPosition, String passCode) {
        TeamModel teamModel = teamModelList.get(selectedPosition);
        presenter.joinTeam(teamModel.getTeamUuid(), passCode);
    }

    @Override
    public void onJoinTeamSucc(TeamResponse teamResponse) {
        addTeamDialog.dismiss();
        showToast(getString(R.string.success_join_team).replace("{team}", teamResponse.getTeamValue()));
    }

    @Override
    public void loadTeam(List<TeamResponse> teamListResponseList) {
        teamModelList.clear();
        teamValueList.clear();
        for (TeamResponse teamResponse : teamListResponseList) {
            teamValueList.add(teamResponse.getTeamValue());
            teamModelList.add(new TeamModel(teamResponse.getTeamUuid(), teamResponse.getTeamValue()));
        }
    }
}
