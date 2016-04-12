package ru.vkastfm.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by german on 19.03.16.
 */
public class TheApp extends Application {

    private static TheApp instance;

    private VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        VKSdk.initialize(this);
    }

    public static TheApp getInstance() {
        return instance;
    }

    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }
}
