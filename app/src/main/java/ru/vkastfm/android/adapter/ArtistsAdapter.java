package ru.vkastfm.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.vkastfm.android.R;
import ru.vkastfm.android.adapter.holder.ArtistHolder;
import ru.vkastfm.android.model.last.Artist;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistHolder> {
    private List<Artist> artists = new ArrayList<>();

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);
        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.bindItem(artist);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void addArtists(List<Artist> artists) {
        int oldSize = this.artists.size();
        this.artists = artists;
        notifyItemRangeInserted(oldSize, artists.size());
    }
}
