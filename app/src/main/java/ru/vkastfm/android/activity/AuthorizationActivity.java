package ru.vkastfm.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import ru.vkastfm.android.DataHelper;
import ru.vkastfm.android.R;
import ru.vkastfm.android.fragment.LastLoginFragment;
import ru.vkastfm.android.fragment.VkLoginFragment;

/**
 * Created by german on 04.04.16.
 */
public class AuthorizationActivity extends AppCompatActivity {
    public static final String TAG = AuthorizationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);

        if (savedInstanceState == null) {
            if (DataHelper.getLastSessionKey() == null) {
                openFragment(new LastLoginFragment(), LastLoginFragment.TAG);
            } else if (VKAccessToken.currentToken() == null) {
                openFragment(new VkLoginFragment(), VkLoginFragment.TAG);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d(TAG, "VkLogin success");
                onVkLogin();
            }

            @Override
            public void onError(VKError error) {
                Log.e(TAG, "VkLogin error " + error);
            }
        })) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onVkLogin() {
        openFragment(new LastLoginFragment(), LastLoginFragment.TAG);
    }

    public void openFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment, tag).commit();
    }
}
