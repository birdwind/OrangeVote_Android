package com.orange.orangetvote.model;

import com.orange.orangetvote.basic.base.BaseModel;
import com.orange.orangetvote.basic.network.RetrofitImpl;
import com.orange.orangetvote.basic.utils.rxHelper.RxObservable;
import com.orange.orangetvote.basic.utils.rxHelper.RxTransformer;
import com.orange.orangetvote.service.VoteService;

public class VoteModel extends BaseModel {

    @Override
    protected VoteService apiService() {
        return RetrofitImpl.createClient().create(VoteService.class);
    }

    public void mVote(RxObservable rxObservable){
        apiService()
                .getVoteList()
                .compose(RxTransformer.switchSchedulers(this))
                .subscribe(rxObservable);
    }

}
