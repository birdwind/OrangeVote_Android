package com.orange.orangetvote.basic.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orange.orangetvote.basic.base.BasePresenter;
import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.basic.config.Config;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.SharedPreferencesUtils;
import com.orange.orangetvote.basic.utils.fragmentNavUtils.FragmentNavigationListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class AbstractFragment<P extends BasePresenter> extends Fragment implements BaseView, BaseActivity<P> {
    public Context context;

    private String className;

    private ProgressDialog dialog;

    protected P presenter;

    protected Unbinder unbinder;

    protected FragmentNavigationListener fragmentNavigationListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(className, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        LogUtils.e(className, "onCreateView");
        final View fragmentView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        presenter = createPresenter();
        context = getActivity();
        className = setClassName();
        initData(savedInstanceState);
        initView();
        addListener();
        doSomething();
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.e(className, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    public void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    public void showFileDialog() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("正在下载中,请稍後");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMax(100);
        dialog.show();
    }

    public void hideFileDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void showLoadingDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
        }
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void onErrorCode(int code, String msg) {
        showToast(msg);
    }

    @Override
    public void showLoadingFileDialog() {
        showFileDialog();
    }

    @Override
    public void hideLoadingFileDialog() {
        hideFileDialog();
    }

    @Override
    public void onProgress(long totalSize, long downSize) {
        if (dialog != null) {
            dialog.setProgress((int) (downSize * 100 / totalSize));
        }
    }

    /**
     * activity跳轉（無代參數）
     *
     * @param className class名稱
     */
    public void startActivity(Class<?> className) {
        startActivity(className, null);
    }

    /**
     * activity跳轉（有代參數）
     *
     * @param className class名稱
     * @param bundle bundle
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent(context, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * activity跳轉（無代參數）並結束（）
     *
     * @param className class名稱
     */
    public void startActivityWithFinish(Class<?> className) {
        startActivityWithFinish(className, null);
    }

    /**
     * activity跳轉（有代參數）並結束（）
     *
     * @param className class名稱
     * @param bundle bundle
     */
    public void startActivityWithFinish(Class<?> className, Bundle bundle) {
        startActivity(className, bundle);
    }

    /**
     * activity跳轉至登入（）
     *
     */
    public void startLoginActivityWithFinish() {
        SharedPreferencesUtils.remove(Config.COOKIES);
        // startActivity(LoginActivity.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentNavigationListener){
            fragmentNavigationListener = (FragmentNavigationListener)context;
        }
        LogUtils.e(className, "OnAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e(className, "onStart");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e(className, "onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e(className, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e(className, "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.e(className, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(className, "onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e(className, "onDetach");
    }

    @Override
    public void onLoginError() {
        ((AbstractActivity)context).startLoginActivityWithFinish();
    }
}
