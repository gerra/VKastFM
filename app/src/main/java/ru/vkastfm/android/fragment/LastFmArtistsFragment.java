package ru.vkastfm.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.vkastfm.android.R;
import ru.vkastfm.android.adapter.ArtistsAdapter;
import ru.vkastfm.android.fragment.base.BaseFragment;
import ru.vkastfm.android.net.RestClient;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by german on 21.04.16.
 */
public class LastFmArtistsFragment extends BaseFragment {
    private RecyclerView artistsView;
    private ArtistsAdapter adapter;
    private Subscription subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (adapter == null) {
            adapter = new ArtistsAdapter();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artists_list, container, false);
        artistsView = (RecyclerView) view.findViewById(R.id.list);
        artistsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        artistsView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (subscription == null) {
            subscription = RestClient.getInstance()
                    .getUserTopArtists("warrior_c")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getTopArtists -> {
                        adapter.addArtists(getTopArtists.getTopArtists());
                    }, throwable -> {
                        if (getView() != null) {
                            Snackbar.make(getView(), "Faaail", Snackbar.LENGTH_SHORT).setAction("Reply", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }
                    });
        }
    }
}
