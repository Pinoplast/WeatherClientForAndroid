package com.example.andriikolomys.start;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class InputCityActivity extends AppCompatActivity {

    Button btnSetCity;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_city);


        btnSetCity = (Button)findViewById(R.id.btn_set_city);
        editText = (EditText)findViewById(R.id.edt_city);
    }

    public void setPosition(View view) throws InterruptedException {

        switch (view.getId()){
            case R.id.btn_set_city:
                Intent intent = new Intent(this, MonthWeatherActivity.class);
                GetWeatherTask asyncTask = new GetWeatherTask(editText.getText().toString(), intent);
                asyncTask.execute();
                break;
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