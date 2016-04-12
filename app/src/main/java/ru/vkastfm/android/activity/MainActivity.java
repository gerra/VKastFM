package ru.vkastfm.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vk.sdk.VKAccessToken;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import ru.vkastfm.android.DataHelper;
import ru.vkastfm.android.R;
import ru.vkastfm.android.net.RestClient;
import ru.vkastfm.android.net.response.GetTopArtists;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                RestClient.getInstance().getUserTopArtists("warrior_c")
                        .subscribe(new Action1<GetTopArtists>() {
                            @Override
                            public void call(GetTopArtists response) {
                                Log.d(TAG, response.getTopArtists().toString());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                if (throwable instanceof HttpException) {
                                    HttpException httpThrowable = (HttpException) throwable;
                                    try {
                                        Log.e(TAG, httpThrowable.response().errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }
        });

        if (VKAccessToken.currentToken() == null || DataHelper.getLastSessionKey() == null) {
            Intent intent = new Intent(this, AuthorizationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
