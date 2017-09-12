package hu.muller.andris.armando.makeithappen_withfriends.Weather;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.DoubleBuffer;

/**
 * Created by Muller Andras on 9/11/2017.
 */

public class DownloadWeatherDataTask extends AsyncTask<String,Void,String> {
    String result = "";
    URL url;
    HttpURLConnection urlConnection = null;
    Double temperature = null;
    String placeName = null;

    @Override
    protected String doInBackground(String... urls) {

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);

            int data = reader.read();
            while (data != -1){
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            return result;

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            temperature = changeKelvinToCelsius(Double.parseDouble(weatherData.getString("temp")));
            placeName = jsonObject.getString("name");

            WheaterFragment.tempTextView.setText(temperature.toString());
            WheaterFragment.cityTextView.setText(placeName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public double changeKelvinToCelsius(double kelvin){
        double celsius = kelvin - 273.15;
        return celsius;
    }

}
