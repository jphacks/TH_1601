package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang.RandomStringUtils;
import org.w3c.dom.Text;

import java.util.Date;

import jp.line.android.sdk.LineSdkContext;
import jp.line.android.sdk.LineSdkContextManager;
import jp.line.android.sdk.api.ApiClient;
import jp.line.android.sdk.api.ApiRequestFuture;
import jp.line.android.sdk.api.ApiRequestFutureListener;
import jp.line.android.sdk.login.LineAuthManager;
import jp.line.android.sdk.login.LineLoginFuture;
import jp.line.android.sdk.login.LineLoginFutureListener;
import jp.line.android.sdk.model.Profile;

import static com.stonedot.todo.smartwalk.R.mipmap.line_icon;

// TODO LINEじゃなくて、最新メッセージを表示するフラグメント、LatestNotificationFragmentとかであるべき？
public class LINEFragment extends Fragment {

    private Activity mActivity;
    private View mFragment;

    private ImageView mLatestSNS;
    private TextView mLatestSender;
    private TextView mLatestContent;
    private TextView mLatestTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        mFragment = inflater.inflate(R.layout.fragment_line, container, false);
        findViews();
        return mFragment;
    }

    /**
     * UIのインスタンスをメンバ変数として取得
     */
    private void findViews() {
        mLatestSNS = (ImageView) mFragment.findViewById(R.id.latest_sns_image);
        mLatestSender = (TextView) mFragment.findViewById(R.id.latest_sender);
        mLatestContent = (TextView) mFragment.findViewById(R.id.latest_content);
        mLatestTime = (TextView) mFragment.findViewById(R.id.latest_time);
    }

    public void displayText(SNS sns, String sender, String content) {
        mLatestSNS.setImageResource(snsIcon(sns));
        mLatestSender.setText(sender);
        mLatestContent.setText(content);
        mLatestTime.setText(new Date().toString());
    }

    public int snsIcon(SNS sns) {
        switch (sns) {
            case LINE:
                return R.mipmap.line_icon;
            default:
                return 0;
        }
    }

    // TODO 以下3つのメソッドはLINELoginクラスとかに移したほうがいい
    public void openLoginPage() {
        Log.d("LineFragment", "Clicked LINE login button.");
        LineSdkContext sdkContext = LineSdkContextManager.getSdkContext();
        LineAuthManager authManager = sdkContext.getAuthManager();
        LineLoginFuture loginFuture = authManager.login(getActivity());
        loginFuture.addFutureListener(loginComplete);
    }

    private LineLoginFutureListener loginComplete = new LineLoginFutureListener() {
        @Override
        public void loginComplete(LineLoginFuture future) {
            switch (future.getProgress()) {
                case SUCCESS:
                    Log.d("LineFragment", "Line login succeeded.");
                    LineSdkContext sdkContext = LineSdkContextManager.getSdkContext();
                    ApiClient apiClient = sdkContext.getApiClient();
                    apiClient.getMyProfile(myProfileComplete);
                    break;
                case CANCELED:
                    Log.d("LineFragment", "Line login canceled.");
                    break;
                default:
                    Log.d("LineFragment", "Line login go something wrong." + future.getProgress().toString());
                    Log.d("LineFragment", "We are trying fallback.");
                    String mid = RandomStringUtils.randomAlphanumeric(30);
                    UserDataStorage.putLineMid(getActivity(), mid);
                    LINEFriendDialogFragment dialog = new LINEFriendDialogFragment();
                    dialog.show(getFragmentManager(), "line_dialog");
                    break;
            }
        }
    };

    public static final String FILENAME_LINE_ID = "line_id";
    private ApiRequestFutureListener myProfileComplete = new ApiRequestFutureListener<Profile>() {
        @Override
        public void requestComplete(ApiRequestFuture<Profile> future) {
            switch (future.getStatus()) {
                case SUCCESS:
                    Log.d("LineFragment", "Profile retrieving succeeded.");
                    Profile profile = future.getResponseObject();
                    UserDataStorage.putLineMid(getActivity(), profile.mid);
                    LINEFriendDialogFragment dialog = new LINEFriendDialogFragment();
                    dialog.show(getFragmentManager(), "line_dialog");
                    break;
                default:
                    Log.d("LineFragment", "Profile retrieving failed.");
                    break;
            }
        }
    };
}
