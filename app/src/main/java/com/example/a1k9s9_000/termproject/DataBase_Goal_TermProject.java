package com.example.a1k9s9_000.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by 1k9s9_000 on 2016-12-16.
 */
public class DataBase_Goal_TermProject extends SQLiteOpenHelper {

    // 데이터 베이스 생성자
    public DataBase_Goal_TermProject(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 데이터 베이스 만들기
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LIST_CONTROL_GOAL (_id INTEGER PRIMARY KEY AUTOINCREMENT, year1 TEXT, month1 TEXT, day1 TEXT, year2 TEXT, month2 TEXT, day2 TEXT, distance TEXT, calorie TEXT);");
    }

    // 데이터 베이스 업그레이드
    public void onUpgrade(SQLiteDatabase db, int old, int newversion) {

    }

    // 데이터 베이스 삽입
    public void insert(String year1, String month1, String day1, String year2, String month2, String day2, String distance, String calorie) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO LIST_CONTROL_GOAL VALUES(null,'" + year1+"', '" + month1+"', '" +day1+"', '"+year2+"', '"+month2+"', '"+day2+"', '"+ distance+"', '"+calorie+"');");
        db.close();
    }

    // 데이터 베이스 수정
    public void update(String year1, String month1, String day1, String year2, String month2, String day2, String distace, String calorie) {

    }

    // 데이터 베이스 삭제
    public void delete(String year1, String month1, String day1, String year2, String month2, String day2, String distance, String calorie) {

    }

    // 거리 데이터 return
    public String getResult_distance(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_dis ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_dis = cursor.getString(7);
        }
        return result_dis;
    }

    // 칼로리 데이터 return
    public String getResult_calorie(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_cal = "";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[]{id});

        if(cursor.moveToFirst()) {
            result_cal = cursor.getString(8);
        }
        return result_cal;
    }

    // year1 data return
    public String getResult_year1(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_year1 ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_year1 = cursor.getString(1);
        }
        return result_year1;
    }

    // month1 data return
    public String getResult_month1(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_month1 ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_month1 = cursor.getString(2);
        }
        return result_month1;
    }

    // day1 data return
    public String getResult_day1(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_day1 ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_day1 = cursor.getString(3);
        }
        return result_day1;
    }

    // year2 data return
    public String getResult_year2(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_year2 ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_year2 = cursor.getString(4);
        }
        return result_year2;
    }

    // month2 data return
    public String getResult_month2(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_month2 ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_month2 = cursor.getString(5);
        }
        return result_month2;
    }

    // day2 data return
    public String getResult_day2(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_day2 ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_day2 = cursor.getString(6);
        }
        return result_day2;
    }

    // year1 + month1 + day1 data return
    public String getResult_date1(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_date1 ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_date1 = cursor.getString(1)+"."+cursor.getString(2)+"."+cursor.getString(3);
        }
        return result_date1;
    }

    // year2 + month2 + day2 data return
    public String getResult_date2(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String result_date2 ="";

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL WHERE _id= ?", new String[] {id});

        if(cursor.moveToFirst()) {
            result_date2 = cursor.getString(4)+"."+cursor.getString(5)+"."+cursor.getString(6);
        }

        return result_date2;
    }

    // AlertDialog 선택 메뉴를 위한 데이터 return
    // 여기서 return 된 배열 안 원소는 선택 메뉴를 구성하는 원소들이 된다.
    public ArrayList<CharSequence> getResult_select_menu() {
        SQLiteDatabase db = getWritableDatabase();
        String result_str ="";
        ArrayList<CharSequence> temp_select = new ArrayList<CharSequence>();

        Cursor cursor = db.rawQuery("SELECT * FROM LIST_CONTROL_GOAL",null);

        while(cursor.moveToNext()) {
            result_str = cursor.getString(1)+"."+cursor.getString(2)+"."+cursor.getString(3) +" ~ "+cursor.getString(4)+"."+cursor.getString(5)+"."+cursor.getString(6);
            temp_select.add(result_str);
        }

        return temp_select;
    }
}
