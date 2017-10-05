package hu.muller.andris.armando.makeithappen_withfriends.Homescreen;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hu.muller.andris.armando.makeithappen_withfriends.R;

public class HomeScreenActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        pager = (ViewPager) findViewById(R.id.homescreen_pager);
        pagerAdapter = new HomeScreenPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

    }
}
