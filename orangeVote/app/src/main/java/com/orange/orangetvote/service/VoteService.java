package com.orange.orangetvote.service;

import com.orange.orangetvote.basic.base.BaseService;
import com.orange.orangetvote.bean.VoteBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface VoteService extends BaseService {

    @GET("api/vote/list")
    Observable<VoteBean> getVoteList();
}
