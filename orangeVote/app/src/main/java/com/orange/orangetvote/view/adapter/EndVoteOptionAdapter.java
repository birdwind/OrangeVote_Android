package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;

public class EndVoteOptionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<String> data;

    private Map<Integer, CustomerEditTextListener> textListenerMap;

    private Map<Integer, CustomerClickListener> customerClickListenerMap;

    public EndVoteOptionAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
        this.textListenerMap = new HashMap<>();
        this.customerClickListenerMap = new HashMap<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setIsRecyclable(false);

        int position = helper.getLayoutPosition();

        if(!item.equals("")){
            helper.getView(R.id.et_append_vote_option).setEnabled(false);
        }
        helper.setText(R.id.et_append_vote_option, item);

        View delete = helper.getView(R.id.iv_append_vote_delete);
        if(delete.getTag() == null) {
            CustomerClickListener customerClickListener = new CustomerClickListener(position);
            delete.setOnClickListener(customerClickListener);
            customerClickListenerMap.put(position, customerClickListener);
            delete.setTag(position);
        }else if(!delete.getTag().equals(position)){
            CustomerClickListener customerClickListener = new CustomerClickListener(position);
            delete.setOnClickListener(customerClickListener);
            delete.setTag(position);
        }

        EditText editText = (EditText) helper.getView(R.id.et_append_vote_option);
        if(editText.getTag() == null) {
            CustomerEditTextListener customerEditTextListener = new CustomerEditTextListener(position);
            textListenerMap.put(position, customerEditTextListener);
            editText.addTextChangedListener(customerEditTextListener);
            editText.setTag(position);
        }else if(!editText.getTag().equals(position)){
            int currentPosition = (int)editText.getTag();
            CustomerEditTextListener currentCustomerEditTextListener = textListenerMap.get(currentPosition);
            editText.removeTextChangedListener(currentCustomerEditTextListener);
            CustomerEditTextListener customerEditTextListener = new CustomerEditTextListener(position);
            editText.addTextChangedListener(customerEditTextListener);
            textListenerMap.put(position, customerEditTextListener);
            editText.setTag(position);
        }
    }

    private class CustomerEditTextListener implements TextWatcher{

        private int position;

        CustomerEditTextListener(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            data.set(position, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private class CustomerClickListener implements View.OnClickListener{

        private int position;

        CustomerClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            data.remove(position);
            textListenerMap.remove(position);
            notifyDataSetChanged();
        }
    }
}
