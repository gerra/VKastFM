package ru.vkastfm.android.net;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
import ru.vkastfm.android.net.response.GetMobileSession;
import ru.vkastfm.android.net.response.GetTopArtists;
import rx.Observable;
import rx.schedulers.Schedulers;

public class RestClient {
    private static final String TAG = RestClient.class.getSimpleName();

    public static final String BASE_URL = "https://ws.audioscrobbler.com/";

    private LastService lastService;
    private static RestClient instance;

    private static String md5(String s) {
        StringBuilder md5Builder = new StringBuilder();
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
            for (byte b : digest) {
                md5Builder.append(String.format("%02x", b & 0xff));
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
        }
        return md5Builder.toString();
    }

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
                    String lastSessionKey = DataHelper.getLastSessionKey();
                    if (lastSessionKey != null) {
                        urlBuilder.addQueryParameter("sk", lastSessionKey);
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
        lastService = retrofit.create(LastService.class);
    }

    public Observable<GetMobileSession> authorizeInLast(String userName, String password) {
        String apiKey = TheApp.getInstance().getString(R.string.lastApiKey);
        String apiSecret = TheApp.getInstance().getString(R.string.lastSecret);

        String apiSig = "api_key" + apiKey
                + "method" + "auth.getMobileSession"
                + "password" + password
                + "username" + userName
                + apiSecret;

        return lastService.getMobileSession("auth.getMobileSession", userName, password, md5(apiSig))
                .subscribeOn(Schedulers.io());
    }

    public Observable<GetTopArtists> getUserTopArtists(String user) {
        return lastService.getUserTopArtists("user.getTopArtists", user)
                .subscribeOn(Schedulers.io());
    }

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }
}
