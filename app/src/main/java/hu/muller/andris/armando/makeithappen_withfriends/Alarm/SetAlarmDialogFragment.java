package hu.muller.andris.armando.makeithappen_withfriends.Alarm;


import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import hu.muller.andris.armando.makeithappen_withfriends.R;
import hu.muller.andris.armando.makeithappen_withfriends.database.DBHelper;
import hu.muller.andris.armando.makeithappen_withfriends.model.Alarm;

import static android.content.Context.ALARM_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetAlarmDialogFragment extends DialogFragment {

    EditText alarmNoteEditText;
    DatePicker alarmDatePicker;
    TimePicker alarmTimePicker;

    String note;
    int year;
    int month;
    int day;
    int hour;
    int minute;

    OnAlarmAddedListener onAlarmAddedListener;
    public interface OnAlarmAddedListener {
        void onAlarmAdded();
    }

    public static SetAlarmDialogFragment newInstance() {
        SetAlarmDialogFragment frag = new SetAlarmDialogFragment();
        return frag;
    }

    public SetAlarmDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=  new  AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.set_alarm))
                .setPositiveButton(R.string.set_alarm,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                note = alarmNoteEditText.getText().toString();

                                year = alarmDatePicker.getYear();
                                month = alarmDatePicker.getMonth();
                                day = alarmDatePicker.getDayOfMonth();

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    hour = alarmTimePicker.getHour();
                                    minute = alarmTimePicker.getMinute();
                                } else{
                                    hour = alarmTimePicker.getCurrentHour();
                                    minute = alarmTimePicker.getCurrentMinute();
                                }

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                calendar.set(Calendar.HOUR_OF_DAY, hour);
                                calendar.set(Calendar.MINUTE, minute);

                                Alarm alarm = new Alarm(note, calendar.getTimeInMillis(), 0, "Nincs");
                                DBHelper db = new DBHelper(getActivity());
                                alarm.setId(db.createAlarm(alarm));

                                Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);
                                PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 0, myIntent, 0);
                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

                                dialog.dismiss();

                                onAlarmAddedListener.onAlarmAdded();

                                Snackbar mySnackbar = Snackbar.make(getActivity().findViewById(R.id.fragment_alarm),
                                        R.string.alarm_set, Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            }
                        }
                )
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialogfragment_set_alarm, null);

        alarmDatePicker = view.findViewById(R.id.alarm_datePicker);
        alarmNoteEditText = view.findViewById(R.id.alarm_note_editText);
        alarmTimePicker = view.findViewById(R.id.alarm_timePicker);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAlarmAddedListener = (OnAlarmAddedListener)context;
    }
}
