package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.view.AbstractActivity;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.model.AddUpdateVoteOptionModel;
import com.orange.orangetvote.presenter.AppendVotePresenter;
import com.orange.orangetvote.request.AppendVoteRequest;
import com.orange.orangetvote.response.team.TeamResponse;
import com.orange.orangetvote.view.adapter.AppendUpdateVoteOptionAdapter;
import com.orange.orangetvote.view.viewCallback.AppendVoteView;
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
    implements AppendVoteView, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private List<String> teamValueList;

    private List<String> teamUuidList;

    private DatePickerDialog datePickerDialog;

    private String currentDate;

    private int currentYear;

    private int currentMonth;

    private int currentDay;

    private AppendUpdateVoteOptionAdapter appendUpdateVoteOptionAdapter;

    private List<AddUpdateVoteOptionModel> voteOptionList;

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
        voteOptionList.add(new AddUpdateVoteOptionModel(""));
        appendUpdateVoteOptionAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_append_vote_confirm)
    void clickBTConfirm() {
        String voteName = etTitle.getText().toString().trim();
        String content = etContent.getText().toString();
        String teamUuid = psvTeam.getSelectedIndex() >= 0 ? teamUuidList.get(psvTeam.getSelectedIndex()) : null;
        String expiredDate = tvDate.getText().toString();
        Boolean isAllowAdd = cbAllowAdd.isChecked();
        Boolean isOpenVoting = cbOpenVoting.isChecked();
        Boolean isSign = cbSign.isChecked();
        int multiply = Integer.parseInt(etMultiply.getText().toString());
        List<String> optionValueList = new ArrayList<>();
        for (AddUpdateVoteOptionModel addUpdateVoteOptionModel : voteOptionList) {
            optionValueList.add(addUpdateVoteOptionModel.getValue());
        }

        AppendVoteRequest appendVoteRequest = new AppendVoteRequest(null, voteName, content, multiply, expiredDate,
            teamUuid, isAllowAdd, isOpenVoting, isSign, optionValueList);
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
        tvDate.setText(currentDate);
        rvOption.setHasFixedSize(true);
        rvOption.setLayoutManager(new LinearLayoutManager(getContext()));
        rvOption.setAdapter(appendUpdateVoteOptionAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        presenter.teamList();
        teamValueList = new ArrayList<>();
        teamUuidList = new ArrayList<>();
        voteOptionList = new ArrayList<>();

        appendUpdateVoteOptionAdapter = new AppendUpdateVoteOptionAdapter(R.layout.component_append_vote_option_item,
            voteOptionList, new ArrayList<>());

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        setCurrentDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog = new DatePickerDialog(context, this, currentYear, currentMonth, currentDay);
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
    public void loadTeamListSuccess(List<TeamResponse> teamListResponseList) {
        teamValueList.clear();
        teamUuidList.clear();
        for (TeamResponse teamResponse : teamListResponseList) {
            teamValueList.add(teamResponse.getTeamValue());
            teamUuidList.add(teamResponse.getTeamUuid());
        }
        psvTeam.setItems(teamValueList);
    }

    @Override
    public void onAppendSuccess() {
        ((AbstractActivity) context).onBackPressed();
    }

    @Override
    public void onClick(View view) {
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setCurrentDate(year, month, dayOfMonth);
        tvDate.setText(currentDate);
    }

    private void setCurrentDate(int year, int month, int dayOfMonth) {
        currentYear = year;
        currentMonth = month;
        currentDay = dayOfMonth;
        currentDate = currentYear + "/" + (currentMonth + 1) + "/" + currentDay;
    }
}
