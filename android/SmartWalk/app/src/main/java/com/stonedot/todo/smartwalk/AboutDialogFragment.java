package com.stonedot.todo.smartwalk;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by komatsu on 2016/11/03.
 */

public class AboutDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View alertView = getActivity().getLayoutInflater().inflate(R.layout.dialog_about, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(R.string.about)
            .setView(alertView)
            .create();
        AlertDialog dialog = alert.show();
        return dialog;
    }
}
