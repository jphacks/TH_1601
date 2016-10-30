package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;

public class ReservationListFragment extends Fragment {

    public interface ReservationListListener {
        void onItemClicked(Reservation reservation);
    }
    private ReservationListListener mListener;

    private View mFragment;
    private ListView mListView;
    private ReservationListAdapter mAdapter;

    public ReservationListFragment() {
        // Required empty public constructor
    }

    public void add(final Reservation item) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.add(item);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragment = inflater.inflate(R.layout.fragment_reservation_list, container, false);
        mAdapter = new ReservationListAdapter(getActivity().getApplicationContext());
        
        mListView = (ListView) mFragment.findViewById(R.id.reservation_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(onItemClick());
        mListView.setOnItemLongClickListener(onItemLongClick());

        ColorDrawable sage = new ColorDrawable(Color.argb(000, 255, 255, 255));
        mListView.setDivider(sage);
        mListView.setDividerHeight(10);

        return mFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReservationListListener) mListener = (ReservationListListener) context;
    }

    private AdapterView.OnItemClickListener onItemClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView listView = (ListView) adapterView;
                Reservation item = (Reservation) listView.getItemAtPosition(i);
                mListener.onItemClicked(item);
                mAdapter.remove(item);
            }
        };
    }

    private AdapterView.OnItemLongClickListener onItemLongClick() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView listView = (ListView) adapterView;
                Reservation item = (Reservation) listView.getItemAtPosition(i);
                mAdapter.remove(item);
                return false;
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}