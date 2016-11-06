package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LINEFriendListFragment extends Fragment {

    private View mFragment;
    private TextView mFriendUrl;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragment = inflater.inflate(R.layout.fragment_friend_list, container, false);
        findViews();
        makeList(getActivity());

        ColorDrawable sage = new ColorDrawable(Color.parseColor("#EEEEEE"));
        mListView.setDivider(sage);
        mListView.setDividerHeight(5);

        return mFragment;
    }

    private void findViews() {
        // mFriendUrl = (TextView) mFragment.findViewById(R.id.line_friend_url);
        mListView = (ListView) mFragment.findViewById(R.id.line_friend_list);
    }

    private void makeList(Activity activity) {
        LINEFriendListAdapter adapter = new LINEFriendListAdapter(activity.getApplicationContext());
        adapter.addAll(friendList());
        mListView.setAdapter(adapter);
    }

    private ArrayList<String> friendList() {
        ArrayList<String> resultList = new ArrayList<String>();
        resultList.add("Pen");
        resultList.add("PineApple");
        resultList.add("Apple");
        resultList.add("Pen");
        return resultList;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
