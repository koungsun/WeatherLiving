package com.example.weatherliving;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class ForecastData {

    private String val_sky, val_temp, tmp_temp, tmp_sky;
    public void ForecastWeatherData(String date, String time, String x, String y) throws IOException, ParseException {

        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        String serviceKey = "fYiWOLjNG%2FRXnb14HGcokit%2FD2gZ0t%2Bslr4PvEv44A4uxgKPaxemUmelx%2BvwQrBX20I6zRxb87%2B2LYqrmcBZJQ%3D%3D";
        String mTime = time+"00";
        Log.d("INFO", "ForecastData date : "+ date+ ", time : "+mTime +", x : "+x+", y :"+y);

        /*URL*/
        String urlBuilder = apiUrl + "?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey +/*Service Key*/
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + /*페이지번호*/
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8") + /*한 페이지 결과 수*/
                "&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8") + /*요청자료형식(XML/JSON) Default: XML*/
                "&" + URLEncoder.encode("base_date", "UTF-8") + "=" + date + /*‘21년 6월 28일발표*/
                "&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("0500", "UTF-8") +  /*05시 발표*/
                "&" + URLEncoder.encode("nx", "UTF-8") + "=" + x + /*예보지점의 X 좌표값*/
                "&" + URLEncoder.encode("ny", "UTF-8") + "=" + y; /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();
        System.out.println(result);

        setForecastdata(result,mTime);
        //Log.d("INFO", "ForecastData result : "+ result);
    }

    public void setForecastdata(String result, String time) throws ParseException {

        // response 키를 가지고 데이터를 파싱
        String weather=null, tmperature = null;

        JSONParser par = new JSONParser();
        JSONObject obj = (JSONObject) par.parse(result);
        // response 키를 가지고 데이터를 파싱
        JSONObject parse_response = (JSONObject) obj.get("response");
        // response 로 부터 body 찾기
        assert parse_response != null;
        JSONObject parse_body = (JSONObject) parse_response.get("body");
        // body 로 부터 items 찾기
        assert parse_body != null;
        JSONObject parse_items = (JSONObject) parse_body.get("items");
        // items로 부터 itemlist 를 받기
        assert parse_items != null;
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        //String category;
        JSONObject mforeobj;

        for(int i = 0; i< Objects.requireNonNull(parse_item).size(); i++) {
            mforeobj = (JSONObject) parse_item.get(i);
            String fcstTime = (String) mforeobj.get("fcstTime");
            String fcstValue = (String) mforeobj.get("fcstValue");
            String category = (String) mforeobj.get("category");
            //Log.d("INFO", "setForecastdata time : "+ time);
            assert fcstTime != null;
            if (fcstTime.equals(time)) {
                assert category != null;
                if (category.equals("SKY")) {
                    weather = "현재 날씨는 ";
                    switch (Objects.requireNonNull(fcstValue)) {
                        case "1":
                            weather += "맑은 상태로, ";
                            tmp_sky = "맑음";
                            break;
                        case "2":
                            weather += "비가 오는 상태로, ";
                            tmp_sky = "비옴";
                            break;
                        case "3":
                            weather += "구름이 많은 상태로, ";
                            tmp_sky = "구름이 많음";
                            break;
                        case "4":
                            weather += "흐린 상태로, ";
                            tmp_sky = "흐림";
                            break;
                    }
                    setVal_sky(tmp_sky);
                }

                //if (category.equals("T3H") || category.equals("T1H")) {
                if (category.equals("TMP")) {
                    tmperature = "기온은 " + fcstValue + "℃ 입니다.";
                    tmp_temp = fcstValue;
                    setVal_temp(tmp_temp);
                }
            }
        }
        Log.d("INFO", "setForecastdata time : "+ time);
        Log.i("WEATER_TAG", weather + tmperature);
    }

    public void setVal_sky(String tmp){ val_sky = tmp;}
    public void setVal_temp(String tmp){ val_temp = tmp;}
    public String getVal_sky(){
        return val_sky;
    }

    public String getVal_temp(){
        return val_temp;
    }
}
