package com.example.a1k9s9_000.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.support.v7.app.AlertDialog;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by 1k9s9_000 on 2016-12-16.
 */
public class DataBase_s_TermProject extends SQLiteOpenHelper{

    //데이터 베이스 생성자
    public DataBase_s_TermProject(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 데이터 베이스 생성
    public void onCreate(SQLiteDatabase dbs) {
        dbs.execSQL("CREATE TABLE LIST_STATISTIC (_id INTEGER PRIMARY KEY AUTOINCREMENT,year TEXT, month TEXT ,day TEXT , place TEXT, event TEXT, latitude TEXT, longitude TEXT);");
    }

    // 데이터 베이스 업그레이드
    public void onUpgrade(SQLiteDatabase dbs, int old, int newversion) {
    }

    // 데이터 추가
    public void insert(String year, String month, String day, String place, String event, String latitude, String longitude) {
        SQLiteDatabase dbs = getWritableDatabase();
        dbs.execSQL("INSERT INTO LIST_STATISTIC VALUES(null,'" + year +"', '"+month+"', '"+day+"', '"+place+"', '"+event+"', '"+latitude+"', '"+longitude+"');");
        dbs.close();
    }

    // 데이터 수정
    public void update(String date, String place, String event) {

    }

    // 데이터 삭제
    public void delete(String date, String place, String event) {
    }

    // 모든 기록 불러오기
    public String getResult() {
        SQLiteDatabase dbs = getWritableDatabase();
        String result = "";

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTIC", null);

        while(cursor.moveToNext()) {
            result += cursor.getString(0) +":" +"\n" + "Date: "+cursor.getString(1)+"."+cursor.getString(2)+"."+cursor.getString(3)+ ", Place: "+ cursor.getString(4) + ", Event: "+cursor.getString(5)+"\n\n";
        }
        dbs.close();
        return result;
    }

    // 하루 통계 불러오기
    public String getResult_oneday(String year, String month, String day) {
        SQLiteDatabase dbs = getWritableDatabase();
        String result = "";

        Cursor cursor = dbs.rawQuery( "SELECT * FROM LIST_STATISTIC WHERE year = ? AND month = ? AND day = ?",new String[] {year, month, day});

        while(cursor.moveToNext()) {
                result += "Place: "+cursor.getString(4) + ", Event: "+cursor.getString(5)+"\n\n";
            }
        dbs.close();
        return result;
    }

    // 일주일 통계 불러오기.
    public String getResult_oneweek(String year, String month, String day, String day1, String day2, String day3, String day4, String day5, String day6) {
        SQLiteDatabase dbs = getWritableDatabase();
        String result = "";

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTIC WHERE year = ? AND month = ? AND (day = ? OR day =? OR day = ? OR day =? OR day = ? OR day = ? OR day = ?)", new String[] {year, month, day,day1,day2,day3,day5,day5,day6});

        while(cursor.moveToNext()) {
            result += "Date: " + cursor.getString(1)+"." +cursor.getString(2) +"." + cursor.getString(3) +": "+"\n" + "Place: " + cursor.getString(4) + ", Event: "+cursor.getString(5)+"\n\n";
        }

        dbs.close();
        return result;
    }

    // 3일 통계
    public String getResult_three(String year, String month, String day, String day1,String day2) {
        SQLiteDatabase dbs = getWritableDatabase();
        String result = "";

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTIC WHERE year = ? AND month = ? AND (day = ? OR day = ? OR day = ?)", new String[] {year,month,day,day1,day2});

        while(cursor.moveToNext()) {
            result += "Date: " + cursor.getString(1) + "." + cursor.getString(2) + "." + cursor.getString(3) + ": " + "\n" + "Place: " + cursor.getString(4) + ", Event: " + cursor.getString(5) + "\n\n";
        }

        dbs.close();
        return result;
    }

    // call in을 위한 함수
    public LinkedList<MapPoint> getResult_Forcallin_latitude(String year, String month, String day) {
        SQLiteDatabase dbs = getWritableDatabase();
        LinkedList<MapPoint> call_in_list = new LinkedList<MapPoint>();

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTIC WHERE year = ? AND month = ? AND day = ?", new String[] {year,month,day});

        while(cursor.moveToNext()) {
            String result = cursor.getString(4) +": " + cursor.getString(5);
            double latitude = Double.parseDouble(cursor.getString(6));
            double longitude = Double.parseDouble(cursor.getString(7));
            call_in_list.add(new MapPoint(result, latitude, longitude));
        }

        return call_in_list;
    }

    // 목표 달성 확인 여부를 위한 거리 합 return 함수
    public String getResult_distance(String year, String month, String day) {
        SQLiteDatabase dbs = getWritableDatabase();
        LinkedList<MapPoint> temp = new LinkedList<MapPoint>();
        double sum = 0;

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTIC WHERE year = ? AND month = ? AND day = ?", new String[] {year, month, day});

        while(cursor.moveToNext()) {
            String result = cursor.getString(4);
            double latitude = Double.parseDouble(cursor.getString(6));
            double longitude = Double.parseDouble(cursor.getString(7));
            temp.add(new MapPoint(result, latitude, longitude));
        }

        for(int i = 0; i < temp.size()-1; i++) {
            double lat1 = temp.get(i).getLatitude();
            double lat2 = temp.get(i+1).getLatitude();
            double longi1 = temp.get(i).getLongitude();
            double longi2 = temp.get(i+1).getLongitude();

            sum += calcDistance(lat1,longi1,lat2,longi2);
        }

        String str_sum = String.valueOf(sum);
        return str_sum;
    }

    // 위도 경도를 이용한 거리 계산 함수
    public double calcDistance(double lat1, double longi1, double lat2, double longi2) {
        double theta, dist;

        theta = longi1 - longi2;
        dist = Math.sin(degtorad(lat1)) * Math.sin(degtorad(lat2)) + Math.cos(degtorad(lat1)) * Math.cos(degtorad(lat2)) * Math.cos(degtorad(theta));
        dist = Math.acos(dist);
        dist = radtodeg(dist);

        dist = dist * 60 * 1.1515;

        // 단위 milli -> km로
        dist = dist * 1.609344;

        // 단위 km -> m로
        dist = dist * 1000.0;

        // 소수점 둘째 자리까지 나타내기
        String format_dist = String.format("%.2f",dist);
        double result = Double.parseDouble(format_dist);

        return result;
    }

    // 주어진 degree 값을 radian 값으로 변환하는 함수
    public double degtorad(double deg) {
        return (double)(deg * Math.PI / (double)180d);
    }

    // 주어진 radian 값을 degree 값으로 변환하는 함수
    public double radtodeg(double rad) {
        return (double)(rad * (double)180d/ Math.PI);
    }
}
