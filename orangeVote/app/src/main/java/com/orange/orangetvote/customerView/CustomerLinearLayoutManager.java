package com.orange.orangetvote.customerView;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CustomerLinearLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public CustomerLinearLayoutManager(Context context, boolean isScrollEnabled) {
        super(context);
        this.isScrollEnabled = isScrollEnabled;
    }

    public CustomerLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
