package com.orange.orangetvote.basic.base;

import android.content.Context;

public abstract class BasePresent<V extends BaseView, M extends BaseModel> implements Present {

    protected V mView;
    protected M mModel;
    protected Context mContext;

    public BasePresent(Context mContext, V mView, M mModel){
        this.mModel = mModel;
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void onDestroy() {
        if(mModel != null){
            mModel.onDestroy();
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
