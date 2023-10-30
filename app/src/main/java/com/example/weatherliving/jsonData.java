package com.example.weatherliving;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class jsonData {
    private String val_code, val_sido, val_gu, val_dong, val_x, val_y;
    private ArrayList<String> arraylist_sido, arraylist_gu, arraylist_dong,arraylist_code;
    String json = "";

    //UV Data
    private String  mvalDate, mUVdata0, mUVdata3, mUVdata6;
    public String getJsonString(InputStream is)
    {
        try {
            //InputStream is = getAssets().open("address.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }
    public void jsonParseSido(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            arraylist_sido = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                val_sido = jsonObject.getString("sido");

                if(!arraylist_sido.contains(val_sido))
                    arraylist_sido.add(val_sido);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void jsonParseGu(String val){
        Log.d("INFO","jsonParseGu val : "+ val);
        try {
            JSONArray jsonArray = new JSONArray(json);
            //arraylist_sido = new ArrayList<>();
            arraylist_gu = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                val_sido = jsonObject.getString("sido");
                val_gu = jsonObject.getString("gu");

                if(val_sido.contains(val)) {
                    //String a = "val : "+val+" val_sido : "+val_sido+" val_gu :"+val_gu;
                    //Log.d("INFO","jsonParseGu a : "+ a);
                    if (!arraylist_gu.contains(val_gu)){
                        String a = " val_sido : "+val_sido+" val_gu :"+val_gu;
                        //Log.d("INFO","jsonParseGu : "+ a);
                        if(val_gu != "")
                            arraylist_gu.add(val_gu);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void jsonParseDong(String v_sido,String v_gu){
        String c = "v_sido : "+v_sido+" v_gu :"+v_gu;
        Log.d("INFO","jsonParseDong val : "+ c);

        try {
            JSONArray jsonArray = new JSONArray(json);

            arraylist_dong = new ArrayList<>();
            //Log.d("INFO","jsonParseDong 111");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                val_sido = jsonObject.getString("sido");
                val_gu = jsonObject.getString("gu");
                val_dong = jsonObject.getString("dong");
                val_code = jsonObject.getString("code");
                //Log.d("INFO","jsonParseDong 222");
                if(val_sido.contains(v_sido)) {
                    if(val_gu.contains(v_gu)) {
                        String d = "val_sido : "+val_sido+" val_gu : "+val_gu;
                        //Log.d("INFO","jsonParseDong 333 :"+d);
                        if (!arraylist_dong.contains(val_dong)) {
                            String b = "val_sido : " + val_sido + " val_gu :" + val_gu+" val_dong : "+val_dong;
                            //Log.d("INFO", "jsonParseDong : " + b);
                            //if(val_dong != "")
                            arraylist_dong.add(val_dong);
                            //arraylist_code.add(val_code);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getAddressCode(String vGu, String val) {
        String ret_code = null;
        String x,y;
        try {
            JSONArray jsonArray = new JSONArray(json);
            arraylist_code = new ArrayList<>();
            //Log.d("INFO", "getAddressCode 111:"+val);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                val_gu = jsonObject.getString("gu");
                val_dong = jsonObject.getString("dong");
                val_code = jsonObject.getString("code");
                x = jsonObject.getString("x");
                y = jsonObject.getString("y");
                if (val_gu.compareTo(vGu) == 0) {
                if (arraylist_dong.contains(val)) {
                    if (val_dong.compareTo(val) == 0) {
                        //Log.d("INFO", "getAddressCode i:" + i);
                        ret_code = val_code;
                        val_x = x;
                        val_y = y;
                        Log.d("INFO", "getAddressCode val_code : " + val_code + ", x :" + x + ", y : " + y);
                    }
                }
            }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.d("INFO", "getAddressCode ret_coded: " + ret_code);

        return ret_code;
    }
    public String getAddressnX() {
        //Log.d("INFO", "getAddressCode val_x: " + val_x);
        return val_x;
    }
    public String getAddressnY() {
        //Log.d("INFO", "getAddressCode val_y: " + val_y);
        return val_y;
    }

    public String getJsonString()
    {
         return json;
    }

    public String getAddressSido() {
        return val_sido;
    }
    public String getAddressGu() {
        return val_gu;
    }
    public String getAddressDong() {
        return val_dong;
    }

    public void setSido(String val) {
        val_sido = val;
        jsonParseGu(val_sido);
    }
    public void setGu(String v1,String v2) {
        val_sido = v1;
        val_gu = v2;
        jsonParseDong(val_sido,val_gu);
    }
    public void setDong(String val) {
        val_dong = val;
    }
    public ArrayList<String> getAddListSido() {
        return arraylist_sido;
    }
    public ArrayList<String> getAddListGu() {
        return arraylist_gu;
    }
    public ArrayList<String> getAddListDong() {
        return arraylist_dong;
    }
}
