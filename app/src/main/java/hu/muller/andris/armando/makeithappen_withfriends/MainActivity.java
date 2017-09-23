package hu.muller.andris.armando.makeithappen_withfriends;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import hu.muller.andris.armando.makeithappen_withfriends.Alarm.AlarmFragment;
import hu.muller.andris.armando.makeithappen_withfriends.Alarm.SetAlarmDialogFragment;
import hu.muller.andris.armando.makeithappen_withfriends.Controlling.ControllingFragment;
import hu.muller.andris.armando.makeithappen_withfriends.Note.NoteFragment;
import hu.muller.andris.armando.makeithappen_withfriends.Todo.NewTodoDialogFragment;
import hu.muller.andris.armando.makeithappen_withfriends.Todo.TodoFragment;

public class MainActivity extends AppCompatActivity implements AlarmFragment.OnNewAlarmListener,
        SetAlarmDialogFragment.OnAlarmAddedListener,
        TodoFragment.OnNewTodoListener, NewTodoDialogFragment.OnTodoAddedListener{

    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_tips:
                showTipsDialog();
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

    void showTipsDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        android.support.v4.app.Fragment prev = fragmentManager.findFragmentByTag("dialog");
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);

        DialogFragment newFragment = new TipsDialogFragment();
        newFragment.show(fragmentManager, "dialog");
    }

    @Override
    public void onNewAlarm() {
        SetAlarmDialogFragment setAlarmDialogFragment = SetAlarmDialogFragment.newInstance();
        setAlarmDialogFragment.show(getSupportFragmentManager(), getString(R.string.set_alarm));
    }

    @Override
    public void onAlarmAdded() {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        if (page instanceof AlarmFragment){
            AlarmFragment alarmFragment = (AlarmFragment)page;
            alarmFragment.update();
        }
    }

    @Override
    public void onNewTodo() {
        NewTodoDialogFragment newTodoDialogFragment = NewTodoDialogFragment.newInstance();
        newTodoDialogFragment.show(getSupportFragmentManager(), getString(R.string.new_todo));
    }

    @Override
    public void onTodoAdded() {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        if (page instanceof TodoFragment){
            TodoFragment todoFragment = (TodoFragment) page;
            todoFragment.update();
        }
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {

//        private Observable mObservers = new FragmentObserver();

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            switch (position){
                case 0:
                    return MainFragment.newInstance();

                case 1:
                    return AlarmFragment.newInstance();

                case 2:
                    return TodoFragment.newInstance();

                case 3:
                    return NoteFragment.newInstance();

                case 4:
                    return ControllingFragment.newInstance();

                default:
                    return MainFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getString(R.string.main_page_title);
                case 1:
                    return getString(R.string.alarm_page_title);
                case 2:
                    return getString(R.string.todo_page_title);
                case 3:
                    return getString(R.string.note_fragment_title);
                case 4:
                    return getString(R.string.controlling_page_title);
            }

            return Resources.getSystem().getString(R.string.error);
        }
    }
}
