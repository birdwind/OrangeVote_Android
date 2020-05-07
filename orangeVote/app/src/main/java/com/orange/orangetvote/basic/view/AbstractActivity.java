package com.orange.orangetvote.basic.view;

import com.jaeger.library.StatusBarUtil;
import com.orange.orangetvote.R;
import com.orange.orangetvote.basic.base.AbstractPresenter;
import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.basic.config.Config;
import com.orange.orangetvote.basic.utils.LogUtils;
import com.orange.orangetvote.basic.utils.SharedPreferencesUtils;
import com.orange.orangetvote.basic.utils.ToastUtils;
import com.orange.orangetvote.view.activity.LoginActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class AbstractActivity<P extends AbstractPresenter> extends AppCompatActivity
    implements BaseView, BaseActivity<P> {
    public Context context;

    private String className;

    private ProgressDialog dialog;

    protected P presenter;

    protected Unbinder unbinder;

    public CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        className = setClassName();
        setContentView(getLayoutId());
        presenter = createPresenter();
        unbinder = ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorTheme));
        initData(savedInstanceState);
        initView();
        addListener();
        doSomething();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View view = getWindow().getDecorView();
            // view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    public void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void addDisposable(Disposable mDisposable){
        if(compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(mDisposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e(className, "onStop");
        clearDisposable();
        if (presenter != null) {
            presenter.detachView();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * @param s
     */
    public void showToast(String s) {
        ToastUtils.show(s);
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
        finish();
    }

    /**
     * activity跳轉至登入（）
     *
     */
    public void logout() {
        SharedPreferencesUtils.remove(Config.COOKIES);
        startActivity(LoginActivity.class);
        finish();
    }

    @Override
    public void onLoginError() {
        logout();
    }

    public void switchFragment(int containerViewId, Fragment fragment, FragmentTransaction fragmentTransaction) {
        switchFrag(containerViewId, fragment, fragmentTransaction);
    }

    public void switchFragmentWithBack(int containerViewId, Fragment fragment,
        FragmentTransaction fragmentTransaction) {
        String backStateName = fragment.getClass().getSimpleName();
        fragmentTransaction.addToBackStack(backStateName);
        switchFrag(containerViewId, fragment, fragmentTransaction);
    }

    private void switchFrag(int containerViewId, Fragment fragment, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    public void popBackAllFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e(className, "onStart");
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return super.dispatchTouchEvent(ev);
            }
        }

        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 獲取輸入框當前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 點選的是輸入框區域，保留點選EditText的事件
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        onBackPress();
        super.onBackPressed();
    }

    protected abstract void onBackPress();
}
