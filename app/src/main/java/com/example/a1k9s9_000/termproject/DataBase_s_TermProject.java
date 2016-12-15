package com.example.a1k9s9_000.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;

/**
 * Created by 1k9s9_000 on 2016-12-16.
 */
public class DataBase_s_TermProject extends SQLiteOpenHelper{

    public DataBase_s_TermProject(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase dbs) {
        dbs.execSQL("CREATE TABLE LIST_STATISTICS (_id INTEGER PRIMARY KEY AUTOINCREMENT,year TEXT, month TEXT ,day TEXT , place TEXT, event TEXT);");
    }

    public void onUpgrade(SQLiteDatabase dbs, int old, int newversion) {
    }

    // 데이터 추가
    public void insert(String year, String month, String day, String place, String event) {
        SQLiteDatabase dbs = getWritableDatabase();
        dbs.execSQL("INSERT INTO LIST_STATISTICS VALUES(null,'" + year +"', '"+month+"', '"+day+"', '"+place+"', '"+event+"');");
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

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTICS", null);

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

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTICS WHERE year = 'year' AND month = 'month' AND day = 'day';",null);

        if(cursor.moveToFirst()) {
           result += "No Data Here. Go Back Please.";
        }
        else {
            while(cursor.moveToNext()) {
                result += "Place: "+cursor.getString(4) + ", Event: "+cursor.getString(5)+"\n\n";
            }
        }
        dbs.close();
        return result;
    }

    // 일주일 통계 불러오기.
    public String getResult_oneweek(String year, String month, String day, String day1, String day2, String day3, String day4, String day5, String day6) {
        SQLiteDatabase dbs = getWritableDatabase();
        String result = "";

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTICS WHERE day = 'day' AND day ='day1' AND day = 'day2' AND day ='day3' AND day = 'day4' AND day = 'day5' AND day = 'day6';", null);

        if(cursor.moveToFirst()) {
            result += "No Data Here. Go Back Please.";
        }
        else {
            while(cursor.moveToNext()) {
                result += "Date: " + cursor.getString(1)+"." +cursor.getString(2) +"." + cursor.getString(3) +": "+"\n" + "Place: " + cursor.getString(4) + ", Event: "+cursor.getString(5)+"\n\n";
            }
        }

        dbs.close();
        return result;
    }

    // 3일 통계
    public String getResult_three(String year, String month, String day, String day1,String day2) {
        SQLiteDatabase dbs = getWritableDatabase();
        String result = "";

        Cursor cursor = dbs.rawQuery("SELECT * FROM LIST_STATISTICS WHERE day = 'day' AND day = 'day1' AND day = 'day2';", null);

        if(cursor.moveToFirst()) {
            result += "No Data Here. Go Back Please.";
        }

        else {
            while(cursor.moveToNext()) {
                result += "Date: " + cursor.getString(1)+"." +cursor.getString(2) +"." + cursor.getString(3) +": "+"\n" + "Place: " + cursor.getString(4) + ", Event: "+cursor.getString(5)+"\n\n";
            }

        }

        dbs.close();
        return result;
    }
}
