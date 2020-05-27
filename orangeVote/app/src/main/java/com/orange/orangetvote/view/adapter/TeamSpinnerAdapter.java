package com.orange.orangetvote.view.adapter;

import com.orange.orangetvote.model.TeamModel;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerInterface;
import com.skydoves.powerspinner.PowerSpinnerView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

//TODO:尚未測試
public class TeamSpinnerAdapter implements PowerSpinnerInterface<TeamModel>, OnSpinnerItemSelectedListener<TeamModel> {

    private OnSpinnerItemSelectedListener<TeamModel> onSpinnerItemSelectedListener;

    private PowerSpinnerView powerSpinnerView;

    private List<TeamModel> teamModelList;

    public TeamSpinnerAdapter(PowerSpinnerView powerSpinnerView, List<TeamModel> teamModelList){
        this.powerSpinnerView = powerSpinnerView;
        this.teamModelList = teamModelList;
        onSpinnerItemSelectedListener = this;
    }

    @Nullable
    @Override
    public OnSpinnerItemSelectedListener<TeamModel> getOnSpinnerItemSelectedListener() {
        return onSpinnerItemSelectedListener;
    }

    @Override
    public void setOnSpinnerItemSelectedListener(@Nullable OnSpinnerItemSelectedListener<TeamModel> onSpinnerItemSelectedListener) {
        this.onSpinnerItemSelectedListener = onSpinnerItemSelectedListener;
    }

    @NotNull
    @Override
    public PowerSpinnerView getSpinnerView() {
       return powerSpinnerView;
    }

    @Override
    public void notifyItemSelected(int i) {
        powerSpinnerView.notifyItemSelected(i, teamModelList.get(i).getTeamValue());
    }

    @Override
    public void setItems(@NotNull List<? extends TeamModel> list) {
        powerSpinnerView.setItems(list);
    }

    @Override
    public void onItemSelected(int i, TeamModel teamModel) {
        powerSpinnerView.notifyItemSelected(i, teamModel.getTeamValue());
    }
}
