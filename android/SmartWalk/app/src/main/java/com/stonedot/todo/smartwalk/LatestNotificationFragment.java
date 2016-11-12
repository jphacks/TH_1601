package com.stonedot.todo.smartwalk;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

public class LatestNotificationFragment extends Fragment {

    public interface LatestNotificationListener {
        void onLatestNotificationClicked(Reservation notification);
    }
    private LatestNotificationListener mListener;

    private View mFragment;
    private TextView mNoLatestNotification;
    private RelativeLayout mLatestNotification;
    private ImageView mSNS;
    private TextView mSender;
    private TextView mContent;
    private TextView mTime;
    private String mTimeFormat;
    private Reservation mLatestReservation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LatestNotificationListener) mListener = (LatestNotificationListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragment = inflater.inflate(R.layout.fragment_latest_notification, container, false);
        mTimeFormat = getActivity().getString(R.string.notification_time_format);
        findViews();
        mLatestNotification.setOnClickListener(reply);
        return mFragment;
    }

    private void findViews() {
        mLatestNotification = (RelativeLayout) mFragment.findViewById(R.id.latest_notification);
        mNoLatestNotification = (TextView) mFragment.findViewById(R.id.no_latest_notification);
        mSNS = (ImageView) mFragment.findViewById(R.id.latest_sns_image);
        mSender = (TextView) mFragment.findViewById(R.id.latest_sender);
        mContent = (TextView) mFragment.findViewById(R.id.latest_content);
        mTime = (TextView) mFragment.findViewById(R.id.latest_time);
    }

    public void displayLatestNotification(SNS sns, String sender, String content) {
        mNoLatestNotification.setVisibility(View.INVISIBLE);
        mLatestReservation = new Reservation(sns, sender, content, new Date());
        mSNS.setImageResource(snsIcon(sns));
        mSender.setText(sender);
        mContent.setText(content);
        mTime.setText(mLatestReservation.getTime(mTimeFormat));
    }

    private int snsIcon(SNS sns) {
        switch (sns) {
            case LINE: return R.mipmap.line_icon;
            default: return 0;
        }
    }

    private View.OnClickListener reply = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mListener == null) return;
            if(mNoLatestNotification.getVisibility() == View.VISIBLE) return;
            if(mLatestReservation != null) mListener.onLatestNotificationClicked(mLatestReservation);
        }
    };
}
