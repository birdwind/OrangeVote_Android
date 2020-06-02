package com.orange.orangetvote.customerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewSupportEmpty extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIsEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIsEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIsEmpty();
        }
    };

    public RecycleViewSupportEmpty(@NonNull Context context) {
        super(context);
    }

    public RecycleViewSupportEmpty(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecycleViewSupportEmpty(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter != null){
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();
    }

    private void checkIsEmpty(){
        Adapter<?> adapter =  getAdapter();
        if(emptyView != null && adapter != null){
            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                this.setVisibility(View.GONE);
            } else {
                emptyView.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setEmptyView(View emptyView){
        this.emptyView = emptyView;
    }
}
