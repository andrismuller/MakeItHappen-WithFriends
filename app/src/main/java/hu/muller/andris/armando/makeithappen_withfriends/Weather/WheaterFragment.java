package hu.muller.andris.armando.makeithappen_withfriends.Weather;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.muller.andris.armando.makeithappen_withfriends.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WheaterFragment extends Fragment {

    DownloadWeatherDataTask downloadWeatherDataTask = new DownloadWeatherDataTask();
    public static TextView tempTextView;
    public static TextView cityTextView;
    String exampleUrl = "http://api.openweathermap.org/data/2.5/weather?q=Budapest&appid=1358e65404bbf025e405a5f58ded63ec";

    public WheaterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadWeatherDataTask.execute(exampleUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wheater, container, false);
        tempTextView = view.findViewById(R.id.temp_textView);
        cityTextView = view.findViewById(R.id.city_textView);

        return view;
    }

}
