package hu.muller.andris.armando.makeithappen_withfriends.Login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import hu.muller.andris.armando.makeithappen_withfriends.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookLoginFragment extends Fragment {
    private static final String TAG = FacebookLoginFragment.class.getSimpleName();
    CallbackManager callbackManager;
    LoginButton loginButton;
    TextView statusTextView;

    public FacebookLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_facebook_login, container, false);
        callbackManager = new CallbackManager.Factory().create();

        statusTextView = (TextView) view.findViewById(R.id.status_textView);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                statusTextView.setText(R.string.login_success + R.string.new_line + R.string.greetings + loginResult.getAccessToken().getUserId() + "!\n");
            }

            @Override
            public void onCancel() {
                statusTextView.setText(R.string.login_canceled);
            }

            @Override
            public void onError(FacebookException exception) {
                statusTextView.setText(R.string.login_error + R.string.new_line + exception.getMessage().toString());
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
