package com.orange.orangetvote.basic.utils.fragmentNavUtils;

import androidx.fragment.app.Fragment;

public interface FragmentNavigationListener {

    void pushFragment(Fragment fragment);

    void updateToolbar(String title, Boolean isShowBack, Boolean isShowClose, Boolean isShowMenu);
}
