package ru.vkastfm.android.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import ru.vkastfm.android.fragment.dialog.ProgressDialogFragment;

/**
 * Created by german on 09.04.16.
 */
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
}
