package ru.vkastfm.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import ru.vkastfm.android.DataHelper;
import ru.vkastfm.android.R;
import ru.vkastfm.android.activity.AuthorizationActivity;
import ru.vkastfm.android.fragment.base.LoginFragment;
import ru.vkastfm.android.net.RestClient;
import rx.Subscription;

public class LastFmLoginFragment extends LoginFragment {
    public static final String TAG = LastFmLoginFragment.class.getSimpleName();

    private Subscription loginSubscription;
    private EditText loginEditText;
    private EditText passwordEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        loginEditText = (EditText) view.findViewById(R.id.loginEdit);
        passwordEditText = (EditText) view.findViewById(R.id.passwordEdit);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginButton.setOnClickListener(v -> {
            String lastFmLogin = loginEditText.getText().toString();
            String lastFmPassword = passwordEditText.getText().toString();
            showProgressDialog();
            loginSubscription = RestClient.getInstance()
                    .authorizeInLastFm(lastFmLogin, lastFmPassword)
                    .subscribe(getMobileSession -> {
                        hideProgressDialog();
                        DataHelper.saveLastFmApiKey(getMobileSession.getSession().getKey());
                        if (getActivity() != null) {
                            ((AuthorizationActivity) getActivity()).onLastFmLogin();
                        }
                    }, throwable -> {
                        hideProgressDialog();
                        if (throwable instanceof HttpException) {
                            HttpException httpThrowable = (HttpException) throwable;
                            try {
                                Log.e(TAG, httpThrowable.response().errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loginSubscription != null) {
            loginSubscription.unsubscribe();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.last_fm_login_fragment;
    }
}
