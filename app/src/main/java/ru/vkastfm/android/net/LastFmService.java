package ru.vkastfm.android.net;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.vkastfm.android.net.response.GetMobileSession;
import ru.vkastfm.android.net.response.GetTopArtists;
import rx.Observable;

public interface LastFmService {

    @POST("/2.0/")
    Observable<GetMobileSession> getMobileSession(@Query("method") String method,
                                               @Query("username") String username,
                                               @Query("password") String password,
                                               @Query("api_sig") String apiSig);

    @GET("/2.0/")
    Observable<GetTopArtists> getUserTopArtists(@Query("method") String method,
                                             @Query("user") String user);

}
