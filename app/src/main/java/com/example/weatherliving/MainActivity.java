package com.example.weatherliving;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String mDate,mTime,mAllDate,mYear,mMon,mDay,mDayText;
    String selectedSido, selectedGu, selectedDong, mAddressCode;
    String strJson;
    ArrayList<String> array_sido, array_gu, array_dong;
    ArrayAdapter<String> arrayAdapter_sido, arrayAdapter_gu, arrayAdapter_dong;

    Spinner dynamic_citySido, dynamic_cityGu, dynamic_cityDong;
    TextView viewtxtPosition, viewtxtDate, viewtxtWeatherInfo,viewtxtWeatherTemp;
    String m_AddressGu,m_AddressDong,viewPosition, viewWeaInfo, viewWeaTemp;

   // int touchCity_sido = -1;
    //int touchCity_gu = -1;

    jsonData json = new jsonData();

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView = findViewById(R.id.textView);
        dynamic_citySido = findViewById(R.id.city_sido);
        dynamic_cityGu = findViewById(R.id.city_gu);
        dynamic_cityDong = findViewById(R.id.city_dong);
        viewtxtPosition = findViewById(R.id.textPos);
        viewtxtDate = findViewById(R.id.textDateTime);
        viewtxtWeatherInfo = findViewById(R.id.textWeatherInfo);
        viewtxtWeatherTemp = findViewById(R.id.textWeatherTmp);

        TimeData mDateTime = new TimeData();
        mDate = mDateTime.getDateAPI();
        mTime = mDateTime.getTimeAPI();
        mAllDate = mDateTime.getDateDataAPI();
        mYear = mDateTime.getYearAPI();
        mMon = mDateTime.getMonAPI();
        mDay = mDateTime.getDayAPI();


        String ttt = "날짜 : "+mDate+", 시간 : "+mTime;
        Log.d("INFO", "Date & Time = " + ttt);
        Log.d("INFO", "All Date & Time = " + mAllDate);

        mDayText = mYear+"년 "+mMon+"월 "+mDay+"일  "+mTime+"시 기준";
        viewtxtDate.setText(mDayText);

        //jsonAddress json = new jsonAddress();
        try {
            InputStream is = getAssets().open("address.json");
            strJson = json.getJsonString(is);
            //Log.d("INFO","json : "+ strJson);
            json.jsonParseSido(strJson);
            array_sido= json.getAddListSido();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        arrayAdapter_sido = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, array_sido);
        arrayAdapter_sido.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dynamic_citySido.setAdapter(arrayAdapter_sido);
        //dynamic_citySido.setSelection(0,false);
        dynamic_citySido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //dynamic_citySido.setSelection(0,false);
                //if(touchCity_sido ==1) {
                    selectedSido = parent.getItemAtPosition(position).toString();
                    dynamic_cityGu.setSelection(0,false);
                    dynamic_cityDong.setSelection(0,false);
                    Log.d("INFO", "SIDO selectedSido = " + selectedSido);
                    json.setSido(selectedSido);
                    array_gu = json.getAddListGu();
                    makeListGu();
                //}
                //touchCity_sido = -1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("INFO","아무것도 선택되지 않았습니다");
            }
        });
        //viewPosition = getGU();.

        //viewPosition = getGU()+" "+getDONG()+" 정보입니다.";
        //viewtxtPosition.setText(viewPosition);


     }
     public void makeListGu(){

         //jsonAddress json = new jsonAddress();
         Log.d("INFO","makeListGu");
         arrayAdapter_gu = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, array_gu);
         arrayAdapter_gu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         dynamic_cityGu.setAdapter(arrayAdapter_gu);
         //dynamic_cityGu.setSelection(0,false);
         dynamic_cityGu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedGu = parent.getItemAtPosition(position).toString();
                 setGU(selectedGu);
                 dynamic_cityDong.setSelection(0,false);
                 Log.d("INFO","GU selectedGu = "+selectedGu);
                 Log.d("INFO", "selectedSido: " + selectedSido + " ,selectedGu: " + selectedGu);
                 json.setGu(selectedSido, selectedGu);
                 array_dong = json.getAddListDong();
                 makeListDong();
             }
             @Override
             public void onNothingSelected(AdapterView<?> parent) {
                 Log.d("INFO","아무것도 선택되지 않았습니다");
             }
         });
     }
    public void makeListDong(){

        //jsonAddress json = new jsonAddress();
        Log.d("INFO","makeListDong");
        arrayAdapter_dong = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, array_dong);
        arrayAdapter_dong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dynamic_cityDong.setAdapter(arrayAdapter_dong);
        //dynamic_cityDong.setSelection(0,false);
        dynamic_cityDong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDong = parent.getItemAtPosition(position).toString();
                setGU(selectedDong);
                Log.d("INFO",selectedGu+" "+selectedDong+"입니다.");
                viewPosition = selectedGu+" "+selectedDong+"입니다.";
                viewtxtPosition.setText(viewPosition);
                mAddressCode = json.getAddressCode(selectedGu,selectedDong);
                String itm = "UV";

                UV_AIR_weatherData wd = new UV_AIR_weatherData();
                new Thread(() -> {
                try {
                    wd.Weatherdata(itm,mDate,mTime,mAddressCode);
                } catch (IOException | ParseException | JSONException e) {
                    throw new RuntimeException(e);
                }
                }).start();


                ForecastData mfore = new ForecastData();
                String x  = json.getAddressnX();
                String y= json.getAddressnY();
                new Thread(() -> {
                try {
                    mfore.ForecastWeatherData(mDate,mTime,x,y);
                    viewWeaInfo = mfore.getVal_sky()+"입니다";
                    viewWeaTemp = mfore.getVal_temp()+"℃";
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
                }).start();


                viewtxtWeatherInfo.setText(viewWeaInfo);
                viewtxtWeatherTemp.setText(viewWeaTemp);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("INFO","아무것도 선택되지 않았습니다");
            }

        });
    }
    void setGU(String val){
        m_AddressGu = val;
    }
    void setDong(String val){
        m_AddressDong = val;
    }
    public String getGU(){
        return m_AddressGu;
    }
    public String getDONG(){
        return m_AddressDong;
    }
}