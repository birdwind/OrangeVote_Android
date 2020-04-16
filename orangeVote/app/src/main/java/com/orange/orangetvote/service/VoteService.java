package com.orange.orangetvote.service;

import com.orange.orangetvote.basic.base.BaseService;
import com.orange.orangetvote.entity.VoteEntity;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface VoteService extends BaseService {

    String voteApi = "api/vote/" ;

    @GET(voteApi + "list")
    Observable<VoteEntity> getVoteList();
}
