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

    // 목표 달성 확인 여부를 위한 거리 구하는 함수
    public String getResult_distance(String year, String month, String day) {
        SQLiteDatabase dbs = getWritableDatabase();
        Location locationA = new Location("Point A");
        Location locationB = new Location("Point B");
        LinkedList<MapPoint> temp = new LinkedList<MapPoint>();

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTIC WHERE year = ? AND month = ? AND day = ?", new String[] {year, month, day});

        while(cursor.moveToNext()) {
            String result = cursor.getString(4);
            double latitude = Double.parseDouble(cursor.getString(6));
            double longitude = Double.parseDouble(cursor.getString(7));
            temp.add(new MapPoint(result, latitude, longitude));
        }

        locationA.setLatitude(temp.get(0).getLatitude());
        locationA.setLongitude(temp.get(0).getLongitude());

        locationB.setLatitude(temp.get(temp.size()-1).getLatitude());
        locationB.setLongitude(temp.get(temp.size()-1).getLongitude());

        double dis = locationA.distanceTo(locationB);
        String final_d = String.valueOf(dis);

        return final_d;
    }
}
