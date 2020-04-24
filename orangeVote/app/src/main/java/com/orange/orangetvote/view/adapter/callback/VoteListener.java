package com.orange.orangetvote.view.adapter.callback;

public interface VoteListener {

    Boolean onClickOption(String voteUuid, String optionUuid);

    void onCancelOption(String voteUuid, String optionUuid);

    Boolean onAddOption(String voteUuid, String option);

    void onCancelAddOption(String voteUuid, String option);

}
