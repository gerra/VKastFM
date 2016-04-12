package ru.vkastfm.android;

import android.support.annotation.Nullable;

/**
 * Created by german on 09.04.16.
 */
public class DataHelper {
    private static final String LAST_SESSION_KEY = "LAST_SESSION_KEY";

    @Nullable
    public static String getLastSessionKey() {
        return TheApp.getInstance().getPreferences().getString(LAST_SESSION_KEY, null);
    }

    public static void saveLastApiKey(String key) {
        TheApp.getInstance().getPreferences().edit()
                .putString(LAST_SESSION_KEY, key)
                .commit();
    }
}
