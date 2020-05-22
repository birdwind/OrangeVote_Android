package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.DateTimeFormatUtils;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.UpdateVotePresenter;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import com.orange.orangetvote.response.vote.VoteDetailResponse;
import com.orange.orangetvote.response.vote.VoteOptionDetailResponse;
import com.orange.orangetvote.view.activity.BottomNavigationActivity;
import com.orange.orangetvote.view.adapter.EndVoteOptionAdapter;
import com.orange.orangetvote.view.adapter.UpdateVoteOptionAdapter;
import com.orange.orangetvote.view.callback.UpdateVoteView;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.takisoft.datetimepicker.widget.DatePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

// TODO: 完善投票詳情
public class VoteDetailFragment extends AbstractFragment<UpdateVotePresenter>
    implements UpdateVoteView, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private String voteUuid;

    private VoteDetailResponse voteDetailResponse;

    private DatePickerDialog datePickerDialog;

    private String currentDate;

    private boolean isEnd;

    private List<String> teamValueList;

    private List<String> teamUuidList;

    private List<VoteOptionDetailResponse> voteOptionDetailResponseList;

    private List<String> voteOptionList;

    private UpdateVoteOptionAdapter updateVoteOptionAdapter;

    private EndVoteOptionAdapter endVoteOptionAdapter;

    @BindView(R.id.et_append_vote_title)
    EditText etTitle;

    @BindView(R.id.et_append_vote_content)
    EditText etContent;

    @BindView(R.id.psv_append_vote_team)
    PowerSpinnerView psvTeam;

    @BindView(R.id.psv_append_vote_date)
    PowerSpinnerView psvDate;

    @BindView(R.id.tv_append_vote_date)
    TextView tvDate;

    @BindView(R.id.rv_append_vote_option)
    RecyclerView rvOption;

    @BindView(R.id.rv_vote_option)
    RecyclerView rvEndOption;

    @BindView(R.id.cb_append_vote_allow_add)
    CheckBox cbAllowAdd;

    @BindView(R.id.cb_append_vote_open_voting)
    CheckBox cbOpenVoting;

    @BindView(R.id.cb_append_vote_sign)
    CheckBox cbSign;

    @BindView(R.id.et_append_vote_multiply)
    EditText etMultiply;

    @BindView(R.id.bt_append_vote_confirm)
    Button btConfirm;

    @OnClick(R.id.btv_append_vote_append_option)
    void clickBTVAppendOption() {
        voteOptionList.add("");
        updateVoteOptionAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_append_vote_confirm)
    void clickBTConfirm() {
        //更新
    }

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
        psvDate.setOnClickListener(this);
    }

    @Override
    public void initView() {
        btConfirm.setText(getString(R.string.update_vote_update));
        rvOption.setHasFixedSize(true);
        rvOption.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOption.setAdapter(updateVoteOptionAdapter);

        rvEndOption.setHasFixedSize(true);
        rvEndOption.setLayoutManager(new LinearLayoutManager(getContext()));
        // rvEndOption.setAdapter(endVoteOptionAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isEnd = false;
        teamValueList = new ArrayList<>();
        teamUuidList = new ArrayList<>();
        voteOptionList = new ArrayList<>();
        voteOptionDetailResponseList = new ArrayList<>();
        if (getArguments() != null) {
            voteUuid = getArguments().getString("voteUuid");
        }
        presenter.teamList();
        presenter.voteDetail(voteUuid);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        datePickerDialog = new DatePickerDialog(context, this, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        updateVoteOptionAdapter =
            new UpdateVoteOptionAdapter(R.layout.component_append_vote_option_item, voteOptionList);
        // endVoteOptionAdapter = new EndVoteOptionAdapter();
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

    @Override
    public void loadVoteDetailSuccess(VoteDetailResponse voteDetailResponse) {
        voteOptionDetailResponseList.clear();
        if (voteDetailResponse == null) {
            showToast(getString(R.string.error_not_found));
            ((BottomNavigationActivity) context).onBackPressed();
        } else {
            int teamValueIndex = -1;
            for (int i = 0; i < teamValueList.size(); i++) {
                if (teamValueList.get(i).equals(voteDetailResponse.getTeam())) {
                    teamValueIndex = i;
                    break;
                }
            }

            fragmentNavigationListener.updateToolbar(voteDetailResponse.getVoteName(), isNeedShowBackOnToolBar(),
                isNeedShowCloseOnToolBar(), isNeedShowMenuOnToolBar());

            currentDate = DateTimeFormatUtils.dateFormat(voteDetailResponse.getExpiredDate());
            voteOptionDetailResponseList.addAll(voteDetailResponse.getOptions());
            for (VoteOptionDetailResponse voteOption : voteOptionDetailResponseList) {
                voteOptionList.add(voteOption.getText());
            }
            updateVoteOptionAdapter.notifyDataSetChanged();

            isEnd = voteDetailResponse.getIsEnd();
            psvTeam.selectItemByIndex(teamValueIndex);
            etTitle.setText(voteDetailResponse.getVoteName());
            etContent.setText(voteDetailResponse.getContent());
            tvDate.setText(currentDate);
            cbAllowAdd.setChecked(voteDetailResponse.getIsAllowAdd());
            cbOpenVoting.setChecked(voteDetailResponse.getIsOpen());
            cbSign.setChecked(voteDetailResponse.getIsSign());
            etMultiply.setText(String.valueOf(voteDetailResponse.getMultiSelection()));
        }
    }

    @Override
    public void onClick(View v) {
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        currentDate = year + "/" + (month + 1) + "/" + dayOfMonth;
        tvDate.setText(currentDate);
    }
}
