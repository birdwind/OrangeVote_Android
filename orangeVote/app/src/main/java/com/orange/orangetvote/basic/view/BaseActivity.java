package com.orange.orangetvote.basic.view;


import com.orange.orangetvote.basic.base.BasePresenter;

public interface BaseActivity<P extends BasePresenter> {

    P createPresenter();

    int getLayoutId();

    void addListener();

    void initView();

    void initData();

    void doSomething();

    default String setClassName() {
        return getClass().getSimpleName();
    };

}
