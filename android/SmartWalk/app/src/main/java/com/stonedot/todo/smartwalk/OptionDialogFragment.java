package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

    private Activity mActivity;
    private Dialog mDialog;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private CheckBox mShutUpWhileActive;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        mDialog = inflateDialog();
        initPreference();
        findViews();
        setEvents();
        loadSettings();
        return mDialog;
    }

    private void initPreference() {
        String prefFile = Preference.SMART_WALK.name();
        mPref = mActivity.getSharedPreferences(prefFile, MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    private void findViews() {
        mShutUpWhileActive = (CheckBox) mDialog.findViewById(R.id.settings_shut_up_while_active);
    }

    private void setEvents() {
        mShutUpWhileActive.setOnClickListener(v -> setPreference(Preference.SHUT_UP_WHILE_ACTIVE.name(), ((CheckBox) v).isChecked()));
    }

    private void loadSettings() {
        mShutUpWhileActive.setChecked(mPref.getBoolean(Preference.SHUT_UP_WHILE_ACTIVE.name(), false));
    }

    private void setPreference(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    private Dialog inflateDialog() {
        View dialogView = mActivity.getLayoutInflater().inflate(R.layout.dialog_option, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.settings).setView(dialogView).create();
        return dialogBuilder.show();
    }
}
