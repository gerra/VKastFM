package ru.vkastfm.android.model.last;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by german on 12.04.16.
 */
public class Artist {
    private String name;
    @SerializedName("playcount")
    private int playCount;
    private String url;
    private int streamable;
    @SerializedName("@attr")
    private Attr attr;
    @SerializedName("image")
    private List<Image> images;

    private static class Attr {
        int rank;
    }
}
