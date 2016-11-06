package com.stonedot.todo.smartwalk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by komatsu on 2016/11/06.
 */

public class OptionDialogFragment extends DialogFragment {
    private static String[] keys = {"is_not_read"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_option, null);

        CheckBox checkBox = (CheckBox) getActivity().findViewById(R.id.checkbox);
        checkBox.setChecked(false);
        checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               CheckBox checkBox = (CheckBox) v;
               boolean checked = checkBox.isChecked();
               SharedPreferences prefer = getContext().getSharedPreferences(getString(R.string.setting_file), MODE_PRIVATE);
               SharedPreferences.Editor editor = prefer.edit();
               editor.putBoolean(keys[0], checked);
               editor.commit();
           }
        });

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.settings)
                .setView(dialogView)
                .create();
        AlertDialog dialog = dialogBuilder.show();

        return dialog;
    }


}
