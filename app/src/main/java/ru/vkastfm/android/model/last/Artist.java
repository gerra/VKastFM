package ru.vkastfm.android.model.last;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
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

    public Artist(String name) {
        this.name = name;
    }

    private static class Attr {
        int rank;
    }

    public String getName() {
        return name;
    }

    public String getLargeImageUrl() {
        return images.get(2).getUrl();
    }
}
