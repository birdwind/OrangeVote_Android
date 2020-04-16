package com.orange.orangetvote.basic.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orange.orangetvote.basic.enums.SystemErrorEnums;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePresent> extends AppCompatActivity {
    public Context mContext;
    public P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        if (setContentViewId() != 0) {
            setContentView(setContentViewId());
        } else {
            throw new RuntimeException(SystemErrorEnums.LAYOUT_RES_ERROR.getMessage());
        }
        createPresenter();
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 設定View layout
     **/
    public abstract int setContentViewId();

    /**
     * 創建Presenter實體
     **/
    public abstract void createPresenter();


    /**
     * 設定View layout
     **/
    protected abstract void initView();

    /**
     * activity跳轉（無代參數）
     *
     * @param className class名稱
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(mContext, className);
        startActivity(intent);
    }

    /**
     * activity跳轉（有代參數）
     *
     * @param className class名稱
     * @param bundle    bundle
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent(mContext, className);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
