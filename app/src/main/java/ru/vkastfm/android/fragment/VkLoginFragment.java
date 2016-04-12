package ru.vkastfm.android.fragment;

import android.view.View;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import ru.vkastfm.android.R;

/**
 * Created by german on 09.04.16.
 */
public class VkLoginFragment extends LoginFragment {
    public static final String TAG = VkLoginFragment.class.getSimpleName();

    @Override
    protected int getLayout() {
        return R.layout.vk_login_fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(getActivity(), VKScope.AUDIO);
            }
        });
    }
}
