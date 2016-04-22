package ru.vkastfm.android.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.vkastfm.android.R;
import ru.vkastfm.android.TheApp;
import ru.vkastfm.android.model.last.Artist;

public class ArtistHolder extends RecyclerView.ViewHolder {
    private TextView titleView;
    private ImageView imgView;

    public ArtistHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.title);
        imgView = (ImageView) itemView.findViewById(R.id.img);
    }

    public void bindItem(Artist artist) {
        titleView.setText(artist.getName());
        Picasso.with(TheApp.getInstance()).load(artist.getLargeImageUrl()).into(imgView);
    }
}
