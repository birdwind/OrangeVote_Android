package com.orange.orangetvote.view.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.view.AbstractFragment;
import com.orange.orangetvote.presenter.AppendVotePresenter;
import com.orange.orangetvote.response.appendVote.TeamListResponse;
import com.orange.orangetvote.view.adapter.AppendVoteOptionAdapter;
import com.orange.orangetvote.view.callback.AppendVoteView;
import com.orange.orangetvote.view.listener.AppendOptionListener;
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
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class AppendVoteFragment extends AbstractFragment<AppendVotePresenter>
    implements AppendVoteView, OnSpinnerItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener,
    BaseQuickAdapter.OnItemChildClickListener, AppendOptionListener {

    private List<String> teamList;

    private DatePickerDialog datePickerDialog;

    private String currentDate;

    private AppendVoteOptionAdapter appendVoteOptionAdapter;

    private AppendOptionListener appendOptionListener;

    private List<String> voteOptionList;

    @BindView(R.id.psv_append_vote_team)
    PowerSpinnerView psvTeam;

    @BindView(R.id.psv_append_vote_date)
    PowerSpinnerView psvDate;

    @BindView(R.id.tv_append_vote_date)
    TextView tvDate;

    @BindView(R.id.rv_append_vote_option)
    RecyclerView rvOption;

    @OnClick(R.id.btv_append_vote_append_option)
    void clickBTVAppendOption() {
        voteOptionList.add("");
        appendVoteOptionAdapter.notifyDataSetChanged();
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
        appendVoteOptionAdapter.setOnItemChildClickListener(this);
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
        teamList = new ArrayList<>();
        voteOptionList = new ArrayList<>();
        appendOptionListener = this;

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
        teamList.clear();
        for (TeamListResponse teamListResponse : teamListResponseList) {
            teamList.add(teamListResponse.getTeamValue());
        }
        psvTeam.setItems(teamList);
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

    @Override
    public void appendOptionListener(String optionValue) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_append_vote_delete:
                voteOptionList.remove(position);
                appendVoteOptionAdapter.notifyDataSetChanged();
                break;
            case R.id.et_append_vote_option:
                EditText editText = (EditText) view;
                RxTextView.textChanges(editText).subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        voteOptionList.set(position, charSequence.toString());
                    }
                });
                break;
        }
    }
}
