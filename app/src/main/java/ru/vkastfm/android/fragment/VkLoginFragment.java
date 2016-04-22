package ru.vkastfm.android.fragment;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import ru.vkastfm.android.R;
import ru.vkastfm.android.fragment.base.LoginFragment;

public class VkLoginFragment extends LoginFragment {
    public static final String TAG = VkLoginFragment.class.getSimpleName();

    @Override
    protected int getLayout() {
        return R.layout.vk_login_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginButton.setOnClickListener(v -> VKSdk.login(getActivity(), VKScope.AUDIO));
    }
}
