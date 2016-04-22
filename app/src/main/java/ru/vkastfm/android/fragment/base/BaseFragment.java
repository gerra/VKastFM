package ru.vkastfm.android.fragment.base;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;

import ru.vkastfm.android.R;
import ru.vkastfm.android.fragment.dialog.ProgressDialogFragment;

public abstract class BaseFragment extends Fragment {

    protected void showProgressDialog() {
        DialogFragment progressFragment =
                (DialogFragment) getFragmentManager().findFragmentByTag(ProgressDialogFragment.TAG);
        if (progressFragment == null || !progressFragment.isVisible()) {
            new ProgressDialogFragment().show(getFragmentManager(), ProgressDialogFragment.TAG);
        }
    }

    protected void hideProgressDialog() {
        DialogFragment progressFragment =
                (DialogFragment) getFragmentManager().findFragmentByTag(ProgressDialogFragment.TAG);
        if (progressFragment != null) {
            progressFragment.dismiss();
        }
    }

    protected void showProgressBar() {
        View progressBar = getView().findViewById(R.id.progress);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    protected void hideProgressBar() {
        View progressBar = getView().findViewById(R.id.progress);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
