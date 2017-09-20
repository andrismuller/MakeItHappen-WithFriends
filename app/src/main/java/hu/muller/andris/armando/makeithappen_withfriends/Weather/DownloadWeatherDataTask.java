package hu.muller.andris.armando.makeithappen_withfriends.Weather;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.DoubleBuffer;
import java.util.HashMap;

/**
 * Created by Muller Andras on 9/11/2017.
 */

public class DownloadWeatherDataTask extends AsyncTask<String,Void,String> {
    private String result = "";
    private URL url;
    private HttpURLConnection urlConnection = null;
    private Double temperature = null;
    private String placeName = null;

    private OnWeatherDataArrivedListener onWeatherDataArrivedListener;

    public interface OnWeatherDataArrivedListener{
        void onWeatherDataArrived(HashMap<String,String> data);
    }

    public DownloadWeatherDataTask(OnWeatherDataArrivedListener weatherDataArrivedListener){
        this.onWeatherDataArrivedListener = weatherDataArrivedListener;
    }

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

            HashMap<String, String> hmWeatherData = new HashMap<>();
            hmWeatherData.put("temperature", temperature.toString());
            hmWeatherData.put("place", placeName);
            onWeatherDataArrivedListener.onWeatherDataArrived(hmWeatherData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private double changeKelvinToCelsius(double kelvin){
        return kelvin - 273.15;
    }

}
