package com.orange.orangetvote.view.callback;

import com.orange.orangetvote.basic.base.BaseView;
import com.orange.orangetvote.response.personal.PersonalResponse;
import java.util.List;

public interface PersonalView extends BaseView {

    void loadPersonalSuccess(List<PersonalResponse> personalResponseList);

    void updatePersonalSuccess(List<PersonalResponse> personalResponseList);
}
