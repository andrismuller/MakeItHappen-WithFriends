package hu.muller.andris.armando.makeithappen_withfriends;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import hu.muller.andris.armando.makeithappen_withfriends.Weather.DownloadWeatherDataTask;
import hu.muller.andris.armando.makeithappen_withfriends.model.FacebookUser;

public class MainFragment extends Fragment implements DownloadWeatherDataTask.OnWeatherDataArrivedListener{
    public static String IMAGE_DIR_PATH = "/data/user/0/hu.muller.andris.armando.makeithappen_withfriends/app_imageDir/";
    public static String meID;

    private static final String ARG_WEATHER = "weather";
    String[] weatherParcialData;
    TextView placeTextView;
    TextView tempTextView;
    TextView welcomeUserTextView;

    private FirebaseAuth mAuth;

    String exampleUrl = "http://api.openweathermap.org/data/2.5/weather?q=Budapest&appid=1358e65404bbf025e405a5f58ded63ec";

    private CallbackManager callbackManager;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Todo: update UI
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
        loginButton.setReadPermissions("email", "user_friends", "public_profile");
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

                                SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                meID = response.getJSONObject().get("id").toString();
                                editor.putString("me", meID);

                                // Commit the edits!
                                editor.commit();

                                JSONArray jsonArrayFriends = null;
                                jsonArrayFriends = object.getJSONObject("friends").getJSONArray("data");
                                for (int i = 0; i < jsonArrayFriends.length(); ++i){
                                    JSONObject friend = jsonArrayFriends.getJSONObject(i);
                                    if (!existFriendInDatabase(friend.getString("name"))){
                                        GetFacebookProfilePicture getPicture = new GetFacebookProfilePicture(friend.getString("id"));
                                        getPicture.execute(new String[]{"https://graph.facebook.com/" + friend.getString("id") + "/picture?type=small"});
                                        FacebookUser facebookUser = new FacebookUser(friend.getString("name"), friend.getString("id"));
                                        facebookUser.setMyId(facebookUser.save());
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,friends");
            request.setParameters(parameters);
            request.executeAsync();
        }

        if (AccessToken.getCurrentAccessToken() != null) {
            AuthCredential credential = FacebookAuthProvider.getCredential(AccessToken.getCurrentAccessToken().getToken());
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("FirebaseLogin", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                // TODO: update ui
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("FirebaseLogin", "signInWithCredential:failure", task.getException());
                                Toast.makeText(getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }

    private class GetFacebookProfilePicture extends AsyncTask<String, Void, String> {
        private String userId;

        public GetFacebookProfilePicture(String userId) {
            this.userId = userId;
        }

        @Override
        protected String doInBackground(String... urls) {
            URL imageURL = null;
            Bitmap bitmap = null;
            String absolutPath = null;
            try {
                imageURL = new URL(urls[0]);
                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                IMAGE_DIR_PATH = saveToInternalStorage(bitmap,userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return absolutPath;
        }

        @Override
        protected void onPostExecute(String result) {}
    }

    public boolean existFriendInDatabase(String name){
        List<FacebookUser> facebookUsers = Select.from(FacebookUser.class)
                .where(Condition.prop("user_name").eq(name))
                .list();
        if (facebookUsers != null && facebookUsers.size() > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onWeatherDataArrived(HashMap<String, String> data) {
        placeTextView.setText(data.get("place"));
        tempTextView.setText(data.get("temperature"));
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String userId){
        ContextWrapper cw = new ContextWrapper(getContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, userId + "_pic.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
