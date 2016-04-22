package ru.vkastfm.android;

import android.support.annotation.Nullable;

public class DataHelper {
    private static final String LAST_FM_SESSION_KEY = "LAST_SESSION_KEY";

    @Nullable
    public static String getLastFmSessionKey() {
        return TheApp.getInstance().getPreferences().getString(LAST_FM_SESSION_KEY, null);
    }

    public static void saveLastFmApiKey(String key) {
        TheApp.getInstance().getPreferences().edit()
                .putString(LAST_FM_SESSION_KEY, key)
                .commit();
    }
}
