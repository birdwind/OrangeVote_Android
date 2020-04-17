package com.orange.orangetvote.view.callback;


import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.entity.VoteResponseEntity;

import java.util.List;

public interface VoteView extends BaseView {

    void onListSucc(List<VoteResponseEntity> o);
}
