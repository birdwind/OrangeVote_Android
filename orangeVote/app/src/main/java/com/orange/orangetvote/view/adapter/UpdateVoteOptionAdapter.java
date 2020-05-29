package com.orange.orangetvote.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orange.orangetvote.R;
import com.orange.orangetvote.model.AddUpdateVoteOptionModel;
import java.util.List;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;

public class UpdateVoteOptionAdapter extends BaseQuickAdapter<AddUpdateVoteOptionModel, BaseViewHolder>
    implements View.OnClickListener {

    private List<String> deleteUuidList;

    private List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList;

    private CustomerEditTextListener customerEditTextListener;

    public UpdateVoteOptionAdapter(int layoutResId,
        @Nullable List<AddUpdateVoteOptionModel> addUpdateVoteOptionModelList, List<String> deleteUuidList) {
        super(layoutResId, addUpdateVoteOptionModelList);
        this.addUpdateVoteOptionModelList = addUpdateVoteOptionModelList;
        this.deleteUuidList = deleteUuidList;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddUpdateVoteOptionModel item) {
        helper.setText(R.id.et_append_vote_option, item.getValue());

        int position = helper.getLayoutPosition();

        View delete = helper.getView(R.id.iv_append_vote_delete);
        delete.setTag(item.getValue());
        delete.setOnClickListener(this);

        // 因為recycleView複用的關係，利用關注與否新增TextWatcher，解決滑動資料誤植
        EditText editText = helper.getView(R.id.et_append_vote_option);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customerEditTextListener = new CustomerEditTextListener(position);
                    editText.addTextChangedListener(customerEditTextListener);
                } else {
                    editText.removeTextChangedListener(customerEditTextListener);
                    customerEditTextListener = null;
                }
            }
        });
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
