package com.orange.orangetvote.view.adapter.callback;

import com.orange.orangetvote.response.voteList.VoteOptionResponse;
import android.view.View;

public interface VoteOptionListener {

    void onClickOption(VoteOptionResponse voteOptionResponse, View view, int position);

    void onIsAddOptionFocus(Boolean isFocus, String oprion);
}
