package com.example.andriikolomys.start;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by andrii.kolomys on 5/16/17.
 */

public class MonthWeatherActivity extends AppCompatActivity  {

    private RecyclerView recView;
    private DerpAdapter adapter;
    private TextView lblCity;
    private String lat, lng;
    private JSONObject cityJson;
    private String data = "";
    private String cityToWeather = "";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_weather);
        lblCity = (TextView)findViewById(R.id.lbl_city);

        recView = (RecyclerView)findViewById(R.id.day_list);

        recView.setLayoutManager(new LinearLayoutManager(this));

        data = getIntent().getStringExtra("json");
        cityToWeather = getIntent().getStringExtra("city");

        lblCity.setText(cityToWeather);

        adapter = new DerpAdapter(DerpDataOfMonth.getDataFromApi(data), this);
        recView.setAdapter(adapter);
    }

    @Override
    public  void onResume(){
        super.onResume();

    }

    @Override
    public void onStart(){
        super.onStart();
    }
}
