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

public class UpdateVoteOptionAdapter extends BaseQuickAdapter<AddUpdateVoteOptionModel, BaseViewHolder>
    implements View.OnClickListener{

    private List<String> deleteUuidList;

    private List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList;

    public UpdateVoteOptionAdapter(int layoutResId,
        @Nullable List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList, List<String> deleteUuidList) {
        super(layoutResId, addUpdateVoteOptionModelList);
        this.addUpdateVoteOptionModelList = addUpdateVoteOptionModelList;
        this.deleteUuidList = deleteUuidList;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
    }

    @Override
    protected void convert(BaseViewHolder helper, AddUpdateVoteOptionModel item) {
        helper.setText(R.id.et_append_vote_option, item.getValue());

        int position = helper.getLayoutPosition();

        View delete = helper.getView(R.id.iv_append_vote_delete);
        delete.setTag(item.getValue());
        delete.setOnClickListener(this);

         EditText editText = helper.getView(R.id.et_append_vote_option);
        // if (editText.getTag() == null) {
        // CustomerEditTextListener customerEditTextListener = new CustomerEditTextListener(position);
        // textListenerMap.put(position, customerEditTextListener);
        // editText.addTextChangedListener(customerEditTextListener);
        // editText.setTag(position);
        // } else if (!editText.getTag().equals(position)) {
        // int currentPosition = (int) editText.getTag();
        // CustomerEditTextListener currentCustomerEditTextListener = textListenerMap.get(currentPosition);
        // editText.removeTextChangedListener(currentCustomerEditTextListener);
        // CustomerEditTextListener customerEditTextListener = new CustomerEditTextListener(position);
        // editText.addTextChangedListener(customerEditTextListener);
        // textListenerMap.put(position, customerEditTextListener);
        // editText.setTag(position);
        // }
    }

    @Override
    public void onClick(View v) {
        for (AddUpdateVoteOptionModel addUpdateVoteOptionModel : addUpdateVoteOptionModelList) {
            if (addUpdateVoteOptionModel.getValue().equals(v.getTag())) {
                if (addUpdateVoteOptionModel.getOptionUuid() != null
                    && !addUpdateVoteOptionModel.getOptionUuid().equals("")) {
                    deleteUuidList.add(addUpdateVoteOptionModel.getOptionUuid());
                }
                addUpdateVoteOptionModelList.remove(addUpdateVoteOptionModel);
                notifyDataSetChanged();
                break;
            }
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
            addUpdateVoteOptionModel.setValue(charSequence.toString().trim());
            addUpdateVoteOptionModel.setUpdate(true);
            addUpdateVoteOptionModelList.set(position, addUpdateVoteOptionModel);
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }

}
