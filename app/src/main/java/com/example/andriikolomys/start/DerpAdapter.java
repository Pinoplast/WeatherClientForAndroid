package com.example.andriikolomys.start;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrii.kolomys on 5/16/17.
 */

public class DerpAdapter extends RecyclerView.Adapter<DerpAdapter.DerpHolder>{
    private List<DataByDay> listData;
    private LayoutInflater inflater;

    public DerpAdapter(List<DataByDay> listData, Context c){
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;

    }

    public DerpAdapter(Example example, Context c){

        this.inflater = LayoutInflater.from(c);
        listData = parseExample(example);
    }

    private List<DataByDay> parseExample(Example  example){

        List<DataByDay> res = new ArrayList<>();

        Forecast days = example.getForecast();

        for (int i = 0; i < days.getForecastday().size(); i++){
            DataByDay day = new DataByDay();
            day.setDayDate(days.getForecastday().get(i).getDate());
            day.setMinT(days.getForecastday().get(i).getDay().getMintempC());
            day.setMaxT(days.getForecastday().get(i).getDay().getMaxtempC());
            day.setId(i);
            //day.setClouds(days.getForecastday().get(i));
            res.add(day);
        }


        return res;
    }

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_weather, parent, false);
        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        DataByDay item = listData.get(position);
        holder.dayDate.setText(item.getDayDate());
        holder.t.setText("from " + item.getMinT() + " to " + item.getMaxT());
        holder.icon.setImageResource(item.getImageId());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder{
        private TextView t;
        private TextView dayDate;
        private ImageView icon;
        private View containter;

        public DerpHolder(View itemView){
            super(itemView);
            dayDate = (TextView)itemView.findViewById(R.id.lbl_day);
            t = (TextView)itemView.findViewById(R.id.lbl_t);
            icon = (ImageView) itemView.findViewById(R.id.item_icon);
            containter = itemView.findViewById(R.id.content_item_root);
        }
    }
}
