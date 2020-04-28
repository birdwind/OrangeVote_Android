package com.orange.orangetvote.basic.view;

import com.orange.orangetvote.basic.utils.LogUtils;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;

public abstract class AbstractDialog<C extends BaseDialogListener> extends Dialog implements BaseDialog{

    protected Context context;

    protected C dialogListener;

    public AbstractDialog(@NonNull Context context, C dialogListener) {
        super(context);
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        LogUtils.e("show");
        ButterKnife.bind(this);
        doSomething();
    }
}
