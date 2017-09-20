package hu.muller.andris.armando.makeithappen_withfriends;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hu.muller.andris.armando.makeithappen_withfriends.Weather.DownloadWeatherDataTask;

public class MainFragment extends Fragment implements DownloadWeatherDataTask.OnWeatherDataArrivedListener{

    private static final String ARG_WEATHER = "weather";
    String[] weatherParcialData;
    TextView placeTextView;
    TextView tempTextView;
    TextView welcomeUserTextView;

    String exampleUrl = "http://api.openweathermap.org/data/2.5/weather?q=Budapest&appid=1358e65404bbf025e405a5f58ded63ec";

    private CallbackManager callbackManager;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){
            weatherParcialData = (String[]) savedInstanceState.get(ARG_WEATHER);
        }
        callbackManager = new CallbackManager.Factory().create();
        DownloadWeatherDataTask downloadWeatherDataTask = new DownloadWeatherDataTask(this);
        downloadWeatherDataTask.execute(exampleUrl);

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        welcomeUserTextView = view.findViewById(R.id.welcome_user_textview);
        setFacebookProfileData();
        LoginButton loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setFacebookProfileData();
            }

            @Override
            public void onCancel() {
                welcomeUserTextView.setText(R.string.login_canceled);
            }

            @Override
            public void onError(FacebookException exception) {
                welcomeUserTextView.setText(R.string.login_error + R.string.new_line + exception.getMessage());
            }
        });

        placeTextView = view.findViewById(R.id.place_main_textView);
        tempTextView = view.findViewById(R.id.temp_main_textView);

        if (weatherParcialData != null){
            placeTextView.setText(weatherParcialData[0]);
            tempTextView.setText(weatherParcialData[1]);
        }

        return view;
    }

    public static Fragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void setFacebookProfileData(){
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            try {
                                welcomeUserTextView.setText(response.getJSONObject().get("name").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    @Override
    public void onWeatherDataArrived(HashMap<String, String> data) {
        placeTextView.setText(data.get("place"));
        tempTextView.setText(data.get("temperature"));
    }
}
