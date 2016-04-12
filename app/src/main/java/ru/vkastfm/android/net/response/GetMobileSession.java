package ru.vkastfm.android.net.response;

/**
 * Created by german on 09.04.16.
 */
public class GetMobileSession {
    public static class MobileSession {
        private String name;
        private String key;
        private int subscriber;

        public String getKey() {
            return key;
        }
    }

    private MobileSession session;

    public MobileSession getSession() {
        return session;
    }
}
