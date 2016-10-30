package com.stonedot.todo.smartwalk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import jp.line.android.sdk.LineSdkContext;
import jp.line.android.sdk.LineSdkContextManager;
import jp.line.android.sdk.api.ApiClient;
import jp.line.android.sdk.api.ApiRequestFuture;
import jp.line.android.sdk.api.ApiRequestFutureListener;
import jp.line.android.sdk.login.LineAuthManager;
import jp.line.android.sdk.login.LineLoginFuture;
import jp.line.android.sdk.login.LineLoginFutureListener;
import jp.line.android.sdk.model.Profile;

public class LINEFragment extends Fragment {

    private Activity mActivity;
    private View mFragment;

    private TextView mTextMessage;
    private Button mButtonLine;

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
        mTextMessage = (TextView) mFragment.findViewById(R.id.text_line_message);
        mButtonLine = (Button)mFragment.findViewById(R.id.buttonForLineLogin);
        Log.d("LineFragment", "Setting event listener.");
        mButtonLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LineFragment", "Clicked LINE login button.");
                LineSdkContext sdkContext = LineSdkContextManager.getSdkContext();
                LineAuthManager authManager = sdkContext.getAuthManager();
                LineLoginFuture loginFuture = authManager.login(getActivity());
                loginFuture.addFutureListener(loginComplete);
            }

        });
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

    public void displayText(String sender, String content) {
        mTextMessage.setText(sender + "\n" + content);
    }
}
