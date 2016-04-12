package ru.vkastfm.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.vkastfm.android.R;

/**
 * Created by german on 09.04.16.
 */
public abstract class LoginFragment extends BaseFragment {

    protected View loginButton;

    protected abstract int getLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        loginButton = view.findViewById(R.id.login);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        loginButton.setOnClickListener(null);
    }
}
