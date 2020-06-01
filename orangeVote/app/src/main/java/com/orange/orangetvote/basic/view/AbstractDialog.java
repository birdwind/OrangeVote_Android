package com.orange.orangetvote.basic.view;

import java.util.Objects;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;

public abstract class AbstractDialog<C extends BaseDialogListener> extends Dialog implements BaseDialog {

    protected Context context;

    protected C dialogListener;

    public AbstractDialog(@NonNull Context context, C dialogListener) {
        super(context);
        this.context = context;
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this);
        doSomething();
    }
}
