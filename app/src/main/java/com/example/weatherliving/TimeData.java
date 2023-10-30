package com.example.weatherliving;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeData {

    private String date, time, dateYear, dateMon, dateDay;

    public String getDateDataAPI(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        //date
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
        String getDate = simpleDateFormat1.format(mDate);

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH");
        String getTime = simpleDateFormat2.format(mDate);// + "00";

        date = getDate+getTime;

        return date;
    }
    public String getDateAPI(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        //date
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
        String getDate = simpleDateFormat1.format(mDate);

        date = getDate;

        return date;
    }
    public String getYearAPI(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        //date
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy");
        String getYearDate = simpleDateFormat1.format(mDate);

        dateYear = getYearDate;

        return dateYear;
    }
    public String getMonAPI(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        //date
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MM");
        String getMonDate = simpleDateFormat1.format(mDate);

        dateMon = getMonDate;

        return dateMon;
    }
    public String getDayAPI(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        //date
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd");
        String getDayDate = simpleDateFormat1.format(mDate);

        dateDay = getDayDate;

        return dateDay;
    }

    public String getTimeAPI(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        //time
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH");
        String getTime = simpleDateFormat2.format(mDate);// + "00";

        time = getTime;

        return time;
    }
}
