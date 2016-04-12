package ru.vkastfm.android.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import ru.vkastfm.android.model.last.Artist;

@SuppressWarnings("unused")
public class GetTopArtists {
    private static class Artists {
        private static class Attr {
            String user;
            int page;
            int perPage;
            int totalPages;
            int total;
        }

        @SerializedName("artist")
        List<Artist> artists;

        @SerializedName("@attr")
        Attr attr;
    }

    @SerializedName("topartists")
    private Artists topArtists;

    public List<Artist> getTopArtists() {
        return topArtists == null ? Collections.EMPTY_LIST : topArtists.artists;
    }
}
