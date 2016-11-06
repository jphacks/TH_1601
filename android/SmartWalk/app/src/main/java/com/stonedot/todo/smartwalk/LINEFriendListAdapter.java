package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by komatsu on 2016/11/06.
 */

public class LINEFriendListAdapter extends ArrayAdapter<String> {

    private View mConvertView;
    private LayoutInflater mInflater;

    public LINEFriendListAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setConvertView(convertView, parent);
        String name = getItem(position);
        setName(name);
        return mConvertView;
    }

    private void setConvertView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            mConvertView = mInflater.inflate(R.layout.line_friend_list_item, parent, false);
            return;
        }
        mConvertView = convertView;
    }

    private void setName(String name) {
        TextView recordName = (TextView) mConvertView.findViewById(R.id.line_friend_name);
        recordName.setText(name);
    }
}
