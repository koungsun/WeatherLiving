package com.example.weatherliving;

import android.util.Log;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class UV_AIR_weatherData {
    private String  mvalDate, mUVdata0, mUVdata3, mUVdata6;
    public String mYear = "";
    public String mMon = "";
    public String mDay = "";
    public String mTime = "";
    public String s = "";
    JSONParser par = new JSONParser();
    public void Weatherdata(String item,String date, String time, String position) throws IOException, JSONException, ParseException {
        String mdateTime = date + time;
        String api;
        Log.d("INFO", "UVweatherData dateTime : "+ mdateTime);
        if(item == "UV")
            api = "getUVIdxV4";
        else{
            api = "getAirDiffusionIdxV4";
        }
        String apiUrl = "http://apis.data.go.kr/1360000/LivingWthrIdxServiceV4/"+api;
        // 홈페이지에서 받은 키
        String serviceKey = "fYiWOLjNG%2FRXnb14HGcokit%2FD2gZ0t%2Bslr4PvEv44A4uxgKPaxemUmelx%2BvwQrBX20I6zRxb87%2B2LYqrmcBZJQ%3D%3D";
        //Log.d("INFO", "UVweatherData11");
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("areaNo", "UTF-8") + "=" + position); /*서울지점*/
        urlBuilder.append("&" + URLEncoder.encode("time", "UTF-8") + "=" + mdateTime);  /*2022년7월11일18시*/
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml, json 선택(미입력시 xml)*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        //Log.d("INFO", "UVweatherData12");

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Log.d("INFO", "url : "+url);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        Log.d("INFO", "Response code:"+ conn.getResponseCode());
        BufferedReader rd;
        //Log.d("INFO", "UVweatherData1");
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        //Log.d("INFO", "UVweatherData15");
        StringBuilder sb = new StringBuilder();
        String line;
        //Log.d("INFO", "UVweatherData16");
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        //Log.d("INFO", "UVweatherData17");
        rd.close();
        conn.disconnect();
        //Log.d("INFO", "UVweatherData18");
        String result = sb.toString();
        //Log.d("INFO", "UV receved data result : "+result);
        //Log.d("INFO", "UVweatherData2");
        if(item == "UV"){
            setUVdata(item, result);
        }
        else {
            setAIRdata(item, result);
        }
    }

    public void setUVdata(String item, String result) throws ParseException {

        //JSONParser par = new JSONParser();
        JSONObject obj = (JSONObject) par.parse(result);
        // response 키를 가지고 데이터를 파싱
        JSONObject parse_response = (JSONObject) obj.get("response");
        // response 로 부터 body 찾기
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        // body 로 부터 items 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        // items로 부터 itemlist 를 받기
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        //String category;
        JSONObject uv; // parse_item은 배열형태이기 때문에 하나씩 데이터를 하나씩 가져올때 사용
        // 카테고리와 값만 받아오기
        //String basedate="";
        //String time="";
        Object ndate = null;
        Object h0 = null;
        for (int i = 0; i < parse_item.size(); i++) {
            uv = (JSONObject) parse_item.get(i);
            ndate = uv.get("date");
            h0 = uv.get("h0");
            Object h3 = uv.get("h3");
            Object h6 = uv.get("h6");
            Object h9 = uv.get("h9");
            Object h12 = uv.get("h12");

            mvalDate = (String) ndate;
            mUVdata0 = (String) h0;
            mUVdata3 = (String) h3;
            mUVdata6 = (String) h6;
            //Log.d("INFO", "setUVdata");
        }
        Log.d("INFO", "자외선지수");
        Log.d("INFO", "date : " + mvalDate);
        Log.d("INFO", "현재 자외선 지수 : " + mUVdata0);
        Log.d("INFO", "3시간후 자외선 지수 : " + mUVdata3);
        Log.d("INFO", "6시간후 자외선 지수 : " + mUVdata6);

        mYear = mvalDate.substring(0,4);
        mMon = mvalDate.substring(4,6);
        mDay = mvalDate.substring(6,8);
        mTime = mvalDate.substring(8,10);

        s = mvalDate + mUVdata0 + mUVdata3 + mUVdata6;
        Log.d("INFO", "setUVdata s ="+s);
    }

    public void setAIRdata(String item, String result) throws ParseException {
        JSONParser par = new JSONParser();
        JSONObject obj = (JSONObject) par.parse(result);
        // response 키를 가지고 데이터를 파싱
        JSONObject parse_response = (JSONObject) obj.get("response");
        // response 로 부터 body 찾기
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        // body 로 부터 items 찾기
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        // items로 부터 itemlist 를 받기
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        //String category;
        JSONObject uv; // parse_item은 배열형태이기 때문에 하나씩 데이터를 하나씩 가져올때 사용
        // 카테고리와 값만 받아오기
        //String basedate="";
        //String time="";
        Object ndate = null;
        Object h0 = null;
        for (int i = 0; i < parse_item.size(); i++) {
            uv = (JSONObject) parse_item.get(i);
            ndate = uv.get("date");
            h0 = uv.get("h0");
            Object h3 = uv.get("h3");
            Object h6 = uv.get("h6");
            Object h9 = uv.get("h9");
            Object h12 = uv.get("h12");

            mvalDate = (String) ndate;
            //mUVdata0 = (String) h0;
            mUVdata3 = (String) h3;
            mUVdata6 = (String) h6;
            Log.d("INFO", "setAIRdata");
        }
        Log.d("INFO", "대기지수");
        Log.d("INFO", "date : " + mvalDate);
        //Log.d("INFO", "현재 자외선 지수 : " + mUVdata0);
        Log.d("INFO", "3시간후 대기 지수 : " + mUVdata3);
        Log.d("INFO", "6시간후 대기 지수 : " + mUVdata6);

        mYear = mvalDate.substring(0,4);
        mMon = mvalDate.substring(4,6);
        mDay = mvalDate.substring(6,8);
        mTime = mvalDate.substring(8,10);

        s = mvalDate + mUVdata3 + mUVdata6;
        Log.d("INFO", "setAIRdata s ="+s);
    }

    public String getUVh0(){
        return mUVdata0;
    }
    public String getUVh3(){
        return mUVdata3;
    }
    public String getUVh6(){
        return mUVdata6;
    }
}

