package com.orange.orangetvote.basic.view;


import com.orange.orangetvote.basic.base.BasePresenter;

public interface BaseFragment<P extends BasePresenter> extends BaseMVPView<P>{

    default Boolean isNeedShowBackOnToolBar(){
        return true;
    }

    default Boolean isNeedShowCloseOnToolBar(){
        return false;
    }

    default Boolean isNeedShowMenuOnToolBar(){
        return false;
    }
}
