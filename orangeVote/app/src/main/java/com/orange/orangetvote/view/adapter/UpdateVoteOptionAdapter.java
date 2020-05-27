package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.model.AddUpdateVoteOptionModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;

public class UpdateVoteOptionAdapter extends BaseQuickAdapter<AddUpdateVoteOptionModel, BaseViewHolder> {

    private List<String> deleteUuidList;

    private List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList;

    private Map<Integer, CustomerEditTextListener> textListenerMap;

    private Map<Integer, CustomerClickListener> customerClickListenerMap;

    public UpdateVoteOptionAdapter(int layoutResId,
        @Nullable List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList, List<String> deleteUuidList) {
        super(layoutResId, addUpdateVoteOptionModelList);
        this.addUpdateVoteOptionModelList = addUpdateVoteOptionModelList;
        this.deleteUuidList = deleteUuidList;
        this.textListenerMap = new HashMap<>();
        this.customerClickListenerMap = new HashMap<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, AddUpdateVoteOptionModel item) {
        helper.setText(R.id.et_append_vote_option, item.getValue());

        int position = helper.getLayoutPosition();

        View delete = helper.getView(R.id.iv_append_vote_delete);

        if (delete.getTag() == null) {
            CustomerClickListener customerClickListener = new CustomerClickListener(position);
            delete.setOnClickListener(customerClickListener);
            customerClickListenerMap.put(position, customerClickListener);
            delete.setTag(position);
        } else if (!delete.getTag().equals(position)) {
            CustomerClickListener customerClickListener = new CustomerClickListener(position);
            delete.setOnClickListener(customerClickListener);
            delete.setTag(position);
        }

        EditText editText = helper.getView(R.id.et_append_vote_option);
        if (editText.getTag() == null) {
            CustomerEditTextListener customerEditTextListener = new CustomerEditTextListener(position);
            textListenerMap.put(position, customerEditTextListener);
            editText.addTextChangedListener(customerEditTextListener);
            editText.setTag(position);
        } else if (!editText.getTag().equals(position)) {
            int currentPosition = (int) editText.getTag();
            CustomerEditTextListener currentCustomerEditTextListener = textListenerMap.get(currentPosition);
            editText.removeTextChangedListener(currentCustomerEditTextListener);
            CustomerEditTextListener customerEditTextListener = new CustomerEditTextListener(position);
            editText.addTextChangedListener(customerEditTextListener);
            textListenerMap.put(position, customerEditTextListener);
            editText.setTag(position);
        }
    }

    private class CustomerEditTextListener implements TextWatcher {

        private int position;

        CustomerEditTextListener(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            AddUpdateVoteOptionModel addUpdateVoteOptionModel = addUpdateVoteOptionModelList.get(position);
            addUpdateVoteOptionModel.setValue(charSequence.toString());
            addUpdateVoteOptionModel.setUpdate(true);
            addUpdateVoteOptionModelList.set(position, addUpdateVoteOptionModel);
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private class CustomerClickListener implements View.OnClickListener {

        private int position;

        CustomerClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            String tempOptionUuid = addUpdateVoteOptionModelList.get(position).getOptionUuid();
            deleteUuidList.add(tempOptionUuid);
            addUpdateVoteOptionModelList.remove(position);
            textListenerMap.remove(position);
            notifyDataSetChanged();
        }
    }
}
