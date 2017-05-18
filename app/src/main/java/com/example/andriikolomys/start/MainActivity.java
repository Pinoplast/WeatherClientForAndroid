package com.example.andriikolomys.start;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnGetPosition, btnGetFromCity;
    TextView txtShow;

    private String city = "";
    private String cityToWeather = "";


    private String lat = "", lng = "";


    private LocationManager locationManager;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetPosition = (Button) findViewById(R.id.btn_get_position);
        btnGetFromCity = (Button) findViewById(R.id.btn_get_from_city);
        btnGetPosition.setEnabled(false);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() throws SecurityException {
        super.onResume();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 10, 10, locationListener);

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000 * 10, 10, locationListener);
        }


        checkEnabled();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"GPS permission granted",Toast.LENGTH_LONG).show();

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                1000 * 10, 10, locationListener);

                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                1000 * 10, 10, locationListener);
                    }


                } else {

                }
                break;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)){
//                lblGps.setText("Status: " + String.valueOf(status));
            }else if (provider.equals(LocationManager.NETWORK_PROVIDER)){
//                lblNetwork.setText("Status: " + String.valueOf(status));
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            try{
                showLocation(locationManager.getLastKnownLocation(provider));
                btnGetPosition.setEnabled(true);
            }
            catch (SecurityException e){

            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }
    };

    private void showLocation(Location location){
        if (location == null){
            return;
        }
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)){

//            lblGpsLocation.setText(formatLocation(location));
        }else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
//            lblNetworkLocation.setText(formatLocation(location));
        }
        btnGetPosition.setEnabled(true);
    }
    private String formatLocation(Location location){
        if (location == null)
        {
            return "";
        }

        lat = String.valueOf(location.getLatitude());
        lng = String.valueOf(location.getLongitude());
        return "Coordinates: lat = "
                + location.getLatitude()
                + ", lon = "
                + location.getLongitude()
                + ", time = "
                + new Date(location.getTime());

    }
    private void checkEnabled(){

//        lblGpsEnabled.setText("Enabled: "
//                + locationManager
//                .isProviderEnabled(LocationManager.GPS_PROVIDER));

//        lblNetworkEnabled.setText("Enabled: "
//                + locationManager
//                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));

    }

    public void getCurrentPosition(View view) throws InterruptedException {

        switch (view.getId()){
            case R.id.btn_get_from_city:
                Intent intentInputCity = new Intent(this, InputCityActivity.class);
                startActivity(intentInputCity);
                break;
            case R.id.btn_get_position:

                Intent intent = new Intent(this, MonthWeatherActivity.class);

                GetCityTask asyncTask = new GetCityTask(lat, lng, intent);
                asyncTask.execute();


                break;
        }

    }
    protected class GetCityTask extends AsyncTask<Void, Void, String>
    {
        Intent intent;

        String lat = "0";
        String lng = "0";

        public GetCityTask(String lat, String lng, Intent intent) {

            this.lat = lat;
            this.lng = lng;
            this.intent = intent;
        }


        @Override
        protected String doInBackground(Void... params)
        {

            String str="http://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }
                Log.e("App", "asd");

                return stringBuffer.toString();
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public String parse(String jsonLine) {
            JsonElement jelement = new JsonParser().parse(jsonLine);
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("results");
            jobject = jarray.get(1).getAsJsonObject();
            String result = jobject.get("formatted_address").toString();
            return result;
        }
        @Override
        protected void onPostExecute(String response)
        {
            if(response != null)
            {
                GetWeatherTask task = new GetWeatherTask(parse(response), intent);
                task.execute();
            }
        }
        protected class GetWeatherTask extends AsyncTask<Void, Void, String>
        {
            Intent intent;
            String city;
            public GetWeatherTask(String city, Intent intent) {

                this.intent = intent;
                this.city = city;
            }

            @Override
            protected String doInBackground(Void... params)
            {

                String str="http://api.apixu.com/v1/forecast.json?key=ac9143c344144a488a2102031171705&q=http://api.apixu.com/v1/current.json?key=ac9143c344144a488a2102031171705&q=" + city + "&days=10\n";
                URLConnection urlConn = null;
                BufferedReader bufferedReader = null;
                try
                {
                    URL url = new URL(str);
                    urlConn = url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        stringBuffer.append(line);
                    }
                    Log.e("App", "asd");

                    return stringBuffer.toString();
                }
                catch(Exception ex)
                {
                    Log.e("App", "yourDataTask", ex);
                    return null;
                }
                finally
                {
                    if(bufferedReader != null)
                    {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            protected void onPostExecute(String response)
            {
                if(response != null)
                {
                    intent.putExtra("city", city);
                    intent.putExtra("json", response);
                    startActivity(intent);
                }
            }
        }
    }

}