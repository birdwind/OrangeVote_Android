package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.AppendVotePresenter;
import com.orange.orangetvote.request.AppendVoteRequest;
import com.orange.orangetvote.request.VoteOptionRequest;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import com.orange.orangetvote.view.adapter.AppendVoteOptionAdapter;
import com.orange.orangetvote.view.callback.AppendVoteView;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.takisoft.datetimepicker.widget.DatePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class AppendVoteFragment extends AbstractFragment<AppendVotePresenter>
    implements AppendVoteView, OnSpinnerItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private List<String> teamValueList;

    private List<String> teamUuidList;

    private DatePickerDialog datePickerDialog;

    private String currentDate;

    private AppendVoteOptionAdapter appendVoteOptionAdapter;

    private List<String> voteOptionList;

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

    @BindView(R.id.cb_append_vote_allow_add)
    CheckBox cbAllowAdd;

    @BindView(R.id.cb_append_vote_open_voting)
    CheckBox cbOpenVoting;

    @BindView(R.id.cb_append_vote_sign)
    CheckBox cbSign;

    @BindView(R.id.et_append_vote_multiply)
    EditText etMultiply;

    @OnClick(R.id.btv_append_vote_append_option)
    void clickBTVAppendOption() {
        voteOptionList.add("");
        appendVoteOptionAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_append_vote_confirm)
    void clickBTConfirm() {
        String voteName = etTitle.getText().toString().trim();
        String content = etContent.getText().toString();
        String teamUuid = teamUuidList.get(psvTeam.getSelectedIndex());
        String expiredDate = tvDate.getText().toString();
        Boolean isAllowAdd = cbAllowAdd.isChecked();
        Boolean isOpenVoting = cbOpenVoting.isChecked();
        Boolean isSign = cbSign.isChecked();
        int multiply = Integer.parseInt(etMultiply.getText().toString());
        List<VoteOptionRequest> voteOptionRequestList = new ArrayList<>();
        for (String voteOption : voteOptionList) {
            voteOptionRequestList.add(new VoteOptionRequest(null, voteOption));
        }
        AppendVoteRequest appendVoteRequest = new AppendVoteRequest(null, voteName, content, multiply, expiredDate,
            teamUuid, isAllowAdd, isOpenVoting, isSign, voteOptionRequestList);
        presenter.appendVote(appendVoteRequest);
    }

    @Override
    public AppendVotePresenter createPresenter() {
        return new AppendVotePresenter(this);
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
        psvTeam.setOnSpinnerItemSelectedListener(this);
        tvDate.setText(currentDate);
        rvOption.setHasFixedSize(true);
        rvOption.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOption.setAdapter(appendVoteOptionAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        presenter.teamList();
        teamValueList = new ArrayList<>();
        teamUuidList = new ArrayList<>();
        voteOptionList = new ArrayList<>();

        appendVoteOptionAdapter =
            new AppendVoteOptionAdapter(R.layout.component_append_vote_option_item, voteOptionList);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        currentDate = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
            + calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(context, this, calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void doSomething() {

    }

    @Override
    protected String setTitle() {
        return getString(R.string.navigation_add);
    }

    @Override
    public Boolean isNeedShowBackOnToolBar() {
        return false;
    }

    @Override
    public void onItemSelected(int i, Object o) {

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
    public void onClick(View view) {
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        currentDate = year + "/" + (month + 1) + "/" + dayOfMonth;
        tvDate.setText(currentDate);
    }

}
