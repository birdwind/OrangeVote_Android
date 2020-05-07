package com.orange.orangetvote.basic.view;


import com.orange.orangetvote.basic.base.AbstractPresenter;
import android.os.Bundle;

public interface BaseMVPView<P extends AbstractPresenter> {

    P createPresenter();

    int getLayoutId();

    void addListener();

    void initView();

    void initData(Bundle savedInstanceState);

    void doSomething();

    default String setClassName() {
        return getClass().getSimpleName();
    };

}
