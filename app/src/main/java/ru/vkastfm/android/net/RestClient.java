package ru.vkastfm.android.net;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vkastfm.android.DataHelper;
import ru.vkastfm.android.R;
import ru.vkastfm.android.TheApp;
import ru.vkastfm.android.Utils;
import ru.vkastfm.android.net.response.GetMobileSession;
import ru.vkastfm.android.net.response.GetTopArtists;
import rx.Observable;
import rx.schedulers.Schedulers;

public class RestClient {
    private static final String TAG = RestClient.class.getSimpleName();

    public static final String BASE_URL = "https://ws.audioscrobbler.com/";

    private LastFmService lastFmService;
    private static RestClient instance;

    private RestClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    String apiKey = TheApp.getInstance().getString(R.string.lastApiKey);
                    HttpUrl.Builder urlBuilder = request.url().newBuilder()
                            .addQueryParameter("api_key", apiKey)
                            .addQueryParameter("format", "json");
                    String lastFmSessionKey = DataHelper.getLastFmSessionKey();
                    if (lastFmSessionKey != null) {
                        urlBuilder.addQueryParameter("sk", lastFmSessionKey);
                    }
                    request = request.newBuilder().url(urlBuilder.build()).build();
                    return chain.proceed(request);
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        lastFmService = retrofit.create(LastFmService.class);
    }

    public Observable<GetMobileSession> authorizeInLastFm(String userName, String password) {
        String apiKey = TheApp.getInstance().getString(R.string.lastApiKey);
        String apiSecret = TheApp.getInstance().getString(R.string.lastSecret);

        String apiSig = "api_key" + apiKey
                + "method" + "auth.getMobileSession"
                + "password" + password
                + "username" + userName
                + apiSecret;

        return lastFmService.getMobileSession("auth.getMobileSession", userName, password, Utils.md5(apiSig))
                .subscribeOn(Schedulers.io());
    }

    public Observable<GetTopArtists> getUserTopArtists(String user) {
        return lastFmService.getUserTopArtists("user.getTopArtists", user)
                .subscribeOn(Schedulers.io());
    }

    public static RestClient getInstance() {
        if (instance == null) {
            synchronized (RestClient.class) {
                if (instance == null) {
                    instance = new RestClient();
                }
            }
        }
        return instance;
    }
}
