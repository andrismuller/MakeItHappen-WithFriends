package hu.muller.andris.armando.makeithappen_withfriends;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import hu.muller.andris.armando.makeithappen_withfriends.Login.FacebookLoginFragment;
import hu.muller.andris.armando.makeithappen_withfriends.Weather.WheaterFragment;

public class MainActivity extends AppCompatActivity {
    FacebookLoginFragment facebookLoginFragment;
    WheaterFragment wheaterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        facebookLoginFragment = new FacebookLoginFragment();
        wheaterFragment = new WheaterFragment();
        fragmentTransaction.add(R.id.main_frame_up_container, wheaterFragment);
        fragmentTransaction.add(R.id.main_frame_down_container, facebookLoginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
