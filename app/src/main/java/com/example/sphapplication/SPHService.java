package com.example.sphapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.TreeMap;

public class SPHService {

    public TreeMap<String, YearListItem> parseJSON(String s) throws JSONException {
        TreeMap<String, YearListItem> yearMap = new TreeMap<>();
        if (s == null) {
            return yearMap;
        }
        JSONObject jsonObject = new JSONObject(s);
        JSONObject result = jsonObject.getJSONObject("result");
        if (result == null) {
            return yearMap;
        }
        JSONArray records = result.getJSONArray("records");
        if (records ==  null) {
            return yearMap;
        }

        //Assuming all year/quarters are in sorted order. Otherwise records need to
        //explicitly sorted before this loop.
        BigDecimal prevVolume = null;
        for(int i = 0; i < records.length(); i++){
            JSONObject record = records.getJSONObject(i);
            String quarter = record.getString("quarter");
            String volume_of_mobile_data = record.getString("volume_of_mobile_data");
            String[] split = quarter.split("-");
            String year = split[0];
            Log.e(year, volume_of_mobile_data);

            BigDecimal currentVolume = new BigDecimal(volume_of_mobile_data);
            boolean declined = false;
            if (prevVolume != null) {
                declined = currentVolume.compareTo(prevVolume) == -1;
                Log.e("decline", quarter + " => " + prevVolume.toString() + " => " + currentVolume.toString() + " " + new Boolean(declined));
            }

            if (!yearMap.containsKey(year)) {
                yearMap.put(year, new YearListItem(year, currentVolume, declined));
            }else{
                YearListItem yearItem = yearMap.get(year);
                BigDecimal newValue = yearItem.getVolume().add(currentVolume);
                yearItem.setVolume(newValue);
                if (declined == true) {
                    yearItem.setDecilned(true);
                }
                yearMap.put(year, yearItem);
            }
            prevVolume = currentVolume;
        }
        return yearMap;
    }
}
