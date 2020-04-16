package com.orange.orangetvote.basic.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orange.orangetvote.basic.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BasePresent> extends Fragment {

    protected Context mContext;
    public View mRootView;
    public T mPresenter;

    /**
     * 獲得全域Content，以防getActivity()為空
     * @param context context
     * */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            if(getLayoutRes() != 0){
                mRootView = LayoutInflater.from(mContext)
                        .inflate(getLayoutRes(), container, false);
                ButterKnife.bind(this, mRootView);

            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 設定View layout
     **/
    public abstract int getLayoutRes();

    /**
     * 創建Presenter實體
     **/
    public abstract void createPresenter();

    /**
     * 設定View layout
     **/
    protected abstract void initView();

    /**
     * Toast
     **/
    public void showToast(String message){
        ToastUtils.show(message);
    }

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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
