package hu.muller.andris.armando.makeithappen_withfriends.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "Alarm");

        Intent intent1 = new Intent();
        intent1.setClassName("hu.muller.andris.armando.makeithappen_withfriends", "hu.muller.andris.armando.makeithappen_withfriends.MainActivity");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("receiver", "alarm");
        context.startActivity(intent1);
    }
}
