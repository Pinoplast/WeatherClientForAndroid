package com.example.andriikolomys.start;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrii.kolomys on 5/16/17.
 */

public class DerpDataOfMonth {
    private static final int[] ids = {0, 1, 2};
    private static final int[] icons = {android.R.drawable.ic_popup_reminder, android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_delete};
    public String res;

    public static List<DataByDay> getListData(){
        List<DataByDay> res = new ArrayList<>();
    int count = 0;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < ids.length && j < icons.length; j++){
                count++;
                DataByDay item = new DataByDay();
                item.setId(ids[j]);
                item.setDayDate("Day: " + count);
                item.setClouds("cloudy");
                item.setImageId(icons[j]);
                item.setMinT(20+i);
                item.setMaxT(27+i);
                res.add(item);
            }

        }

        return res;
    }
    public static Example getDataFromApi(String data){

        String resq = data;
        JsonElement jelement = new JsonParser().parse(resq);

        Example example;
        Gson gson = new Gson();
        example = gson.fromJson(jelement, Example.class);

        return  example;
    }
}
