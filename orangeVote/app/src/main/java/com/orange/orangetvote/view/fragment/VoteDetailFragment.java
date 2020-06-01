package com.orange.orangetvote.view.fragment;

import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.DateTimeFormatUtils;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.model.AddUpdateVoteOptionModel;
import com.orange.orangetvote.model.TeamModel;
import com.orange.orangetvote.presenter.UpdateVotePresenter;
import com.orange.orangetvote.request.UpdateVoteRequest;
import com.orange.orangetvote.response.team.TeamResponse;
import com.orange.orangetvote.response.vote.VoteDetailResponse;
import com.orange.orangetvote.response.vote.VoteOptionDetailResponse;
import com.orange.orangetvote.view.activity.BottomNavigationActivity;
import com.orange.orangetvote.view.adapter.AppendUpdateVoteOptionAdapter;
import com.orange.orangetvote.view.adapter.EndVoteOptionAdapter;
import com.orange.orangetvote.view.viewCallback.UpdateVoteView;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.takisoft.datetimepicker.widget.DatePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class VoteDetailFragment extends AbstractFragment<UpdateVotePresenter>
    implements UpdateVoteView, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private String voteUuid;

    private VoteDetailResponse voteDetailResponse;

    private DatePickerDialog datePickerDialog;

    private String currentDate;

    private int currentYear;

    private int currentMonth;

    private int currentDay;

    private List<String> teamValueList;

    private List<TeamModel> teamModelList;

    private List<VoteOptionDetailResponse> voteOptionDetailResponseList;

    private List<AddUpdateVoteOptionModel> voteOptionList;

    private List<String> deleteOptionUuidList;

    private AppendUpdateVoteOptionAdapter appendUpdateVoteOptionAdapter;

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

    @BindView(R.id.btv_append_vote_append_option)
    TextView btvAppendOption;

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
        voteOptionList.add(new AddUpdateVoteOptionModel(""));
        appendUpdateVoteOptionAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_append_vote_confirm)
    void clickBTConfirm() {
        // 更新
        List<String> updateOptionValueList = new ArrayList<>();
        List<String> updateOptionUuidList = new ArrayList<>();
        List<String> addOptionValueList = new ArrayList<>();
        String voteName = etTitle.getText().toString();
        String voteContent = etContent.getText().toString();
        boolean isAllowAdd = cbAllowAdd.isChecked();
        boolean isOpen = cbOpenVoting.isChecked();
        boolean isSign = cbSign.isChecked();
        int multiSelection = Integer.parseInt(etMultiply.getText().toString());

        for (AddUpdateVoteOptionModel addUpdateVoteOptionModel : voteOptionList) {
            if (addUpdateVoteOptionModel.getOptionUuid() == null) {
                addOptionValueList.add(addUpdateVoteOptionModel.getValue());
            } else if (addUpdateVoteOptionModel.getIsUpdate()) {
                if (!addUpdateVoteOptionModel.getValue().equals("")) {
                    updateOptionUuidList.add(addUpdateVoteOptionModel.getOptionUuid());
                    updateOptionValueList.add(addUpdateVoteOptionModel.getValue());
                }
            }
        }

        UpdateVoteRequest updateVoteRequest = new UpdateVoteRequest(voteUuid, voteName, voteContent,
            getSelectedTeamUuid(psvTeam.getSelectedIndex()), currentDate, updateOptionUuidList, updateOptionValueList,
            deleteOptionUuidList, addOptionValueList, isAllowAdd, isOpen, isSign, multiSelection);

        presenter.updateVote(updateVoteRequest);
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
        rvOption.setAdapter(appendUpdateVoteOptionAdapter);

        rvEndOption.setHasFixedSize(true);
        rvEndOption.setLayoutManager(new LinearLayoutManager(getContext()));
        rvEndOption.setAdapter(endVoteOptionAdapter);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        teamValueList = new ArrayList<>();
        teamModelList = new ArrayList<>();
        voteOptionList = new ArrayList<>();
        voteOptionDetailResponseList = new ArrayList<>();
        deleteOptionUuidList = new ArrayList<>();
        voteDetailResponse = null;

        if (getArguments() != null) {
            voteUuid = getArguments().getString("voteUuid");
        }
        presenter.teamList();
        presenter.voteDetail(voteUuid);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        setCurrentDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog = new DatePickerDialog(context, this, currentYear, currentMonth, currentDay);

        appendUpdateVoteOptionAdapter = new AppendUpdateVoteOptionAdapter(R.layout.component_append_vote_option_item,
            voteOptionList, deleteOptionUuidList);
        endVoteOptionAdapter =
            new EndVoteOptionAdapter(R.layout.component_vote_option_end_item, voteOptionDetailResponseList);
    }

    @Override
    public void doSomething() {

    }

    @Override
    public void loadTeamListSuccess(List<TeamResponse> teamListResponseList) {
        teamModelList.clear();
        for (TeamResponse teamResponse : teamListResponseList) {
            teamModelList.add(new TeamModel(teamResponse.getTeamUuid(), teamResponse.getTeamValue()));
            teamValueList.add(teamResponse.getTeamValue());
        }
        psvTeam.setItems(teamValueList);
    }

    @Override
    public void loadVoteDetailSuccess(VoteDetailResponse voteDetailResponse) {
        this.voteDetailResponse = voteDetailResponse;
        voteOptionDetailResponseList.clear();
        if (voteDetailResponse == null) {
            showToast(getString(R.string.error_not_found));
            ((BottomNavigationActivity) context).onBackPressed();
        } else {
            fragmentNavigationListener.updateToolbar(voteDetailResponse.getVoteName(), isNeedShowBackOnToolBar(),
                isNeedShowCloseOnToolBar(), isNeedShowMenuOnToolBar());

            setSelectedTeam(voteDetailResponse.getTeam());

            setCurrentDate(voteDetailResponse.getExpiredDate());

            voteOptionDetailResponseList.addAll(voteDetailResponse.getOptions());
            voteOptionList.clear();
            for (VoteOptionDetailResponse voteOption : voteOptionDetailResponseList) {
                voteOptionList.add(new AddUpdateVoteOptionModel(voteOption.getValue(), voteOption.getText(), voteDetailResponse.getIsOwner()));
            }

            etTitle.setText(voteDetailResponse.getVoteName());
            etContent.setText(voteDetailResponse.getContent());
            tvDate.setText(currentDate);
            cbAllowAdd.setChecked(voteDetailResponse.getIsAllowAdd());
            cbOpenVoting.setChecked(voteDetailResponse.getIsOpen());
            cbSign.setChecked(voteDetailResponse.getIsSign());
            etMultiply.setText(String.valueOf(voteDetailResponse.getMultiSelection()));

            // 根據是否已結束來設定顯示頁面
            if(voteDetailResponse.getIsOwner()) {
                if (voteDetailResponse.getIsEnd()) {
                    etTitle.setEnabled(false);
                    etContent.setEnabled(false);
                    psvTeam.setEnabled(false);
                    psvDate.setEnabled(false);
                    cbSign.setClickable(false);
                    cbOpenVoting.setClickable(false);
                    cbAllowAdd.setClickable(false);
                    etMultiply.setEnabled(false);

                    psvTeam.setTextColor(ContextCompat.getColor(context, R.color.colorGrey_text));
                    tvDate.setTextColor(ContextCompat.getColor(context, R.color.colorGrey_text));
                    rvEndOption.setVisibility(View.VISIBLE);
                    rvOption.setVisibility(View.GONE);
                    btvAppendOption.setVisibility(View.GONE);
                    btConfirm.setVisibility(View.GONE);
                    endVoteOptionAdapter.notifyDataSetChanged();
                } else {
                    rvEndOption.setVisibility(View.GONE);
                    rvOption.setVisibility(View.VISIBLE);
                    btvAppendOption.setVisibility(View.VISIBLE);
                    appendUpdateVoteOptionAdapter.notifyDataSetChanged();
                }
            }else{
                etTitle.setEnabled(false);
                etContent.setEnabled(false);
                psvTeam.setEnabled(false);
                psvDate.setEnabled(false);
                cbSign.setClickable(false);
                cbOpenVoting.setClickable(false);
                cbAllowAdd.setClickable(false);
                etMultiply.setEnabled(false);
                psvTeam.setTextColor(ContextCompat.getColor(context, R.color.colorGrey_text));
                tvDate.setTextColor(ContextCompat.getColor(context, R.color.colorGrey_text));
                btvAppendOption.setVisibility(View.GONE);
                btConfirm.setVisibility(View.GONE);
                endVoteOptionAdapter.notifyDataSetChanged();
                appendUpdateVoteOptionAdapter.notifyDataSetChanged();
                if(voteDetailResponse.getIsEnd()){
                    rvEndOption.setVisibility(View.VISIBLE);
                    rvOption.setVisibility(View.GONE);
                }else{
                    rvEndOption.setVisibility(View.GONE);
                    rvOption.setVisibility(View.VISIBLE);
                }
            }
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

    private void setCurrentDate(int year, int month, int dayOfMonth) {
        currentYear = year;
        currentMonth = month;
        currentDay = dayOfMonth;
        currentDate = currentYear + "/" + (currentMonth + 1) + "/" + currentDay;
    }

    private void setCurrentDate(Date selectedDate) {
        String tempCurrentDate = DateTimeFormatUtils.dateFormat(selectedDate);
        String[] tempDate = tempCurrentDate.split("/");
        setCurrentDate(Integer.parseInt(tempDate[0]), Integer.parseInt(tempDate[1]) - 1, Integer.parseInt(tempDate[2]));
        datePickerDialog.getDatePicker().updateDate(currentYear, currentMonth, currentDay);
    }

    private void setSelectedTeam(String teamValue) {
        int teamValueIndex = -1;
        for (int i = 0; i < teamValueList.size(); i++) {
            if (teamValueList.get(i).equals(teamValue)) {
                teamValueIndex = i;
                psvTeam.selectItemByIndex(teamValueIndex);
                break;
            }
        }
    }

    private String getSelectedTeamUuid(int teamPosition) {
        TeamModel teamModel = teamModelList.get(teamPosition);
        return teamModel.getTeamUuid();
    }
}
