package com.example.a1k9s9_000.termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event_Record_TermProject extends AppCompatActivity {
    TextView record_title, place, event, latitude, longitude, time, date, info_lat, info_longi, info_time, info_date;
    EditText info_place, info_event;
    Button photo, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__record__term_project);

        // 데이터 베이스 불러오기.(Insert 위한 데이터 베이스)
        final DataBase_s_TermProject dbs = new DataBase_s_TermProject(getApplicationContext(), "List_statistic.db",null,1);

        record_title = (TextView)findViewById(R.id.record_title);
        place = (TextView)findViewById(R.id.year);
        event = (TextView)findViewById(R.id.month);
        latitude = (TextView)findViewById(R.id.latitude);
        longitude = (TextView)findViewById(R.id.longitude);
        time = (TextView)findViewById(R.id.time);
        date = (TextView)findViewById(R.id.date);
        info_lat = (TextView)findViewById(R.id.info_lat);
        info_longi = (TextView)findViewById(R.id.info_longi);
        info_time = (TextView)findViewById(R.id.info_time);
        info_date = (TextView)findViewById(R.id.info_date);

        info_place = (EditText)findViewById(R.id.info_place);
        info_event = (EditText)findViewById(R.id.info_event);

        photo = (Button)findViewById(R.id.photo);
        save = (Button)findViewById(R.id.save);

        Intent intent = getIntent();
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        final String lat_record = intent.getStringExtra("lat");
        final String longi_record = intent.getStringExtra("long");

        info_lat.setText(lat_record);
        info_longi.setText(longi_record);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");

        String str_dateformat = dateformat.format(date);
        String str_timeformat = timeformat.format(date);

        info_time.setText(str_timeformat);
        info_date.setText(str_dateformat);

        // 사진 찍기 화면으로 이동
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Event_Record_TermProject.this, "Taking Photo!",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(),Image_TermProject.class);
                startActivity(intent1);
            }
        });

        // 데이터 베이스에 해당 기록 내용 Insert
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Event_Record_TermProject.this, "Save Data!", Toast.LENGTH_SHORT).show();
                long now1 = System.currentTimeMillis();
                Date date1 = new Date(now1);
                SimpleDateFormat year = new SimpleDateFormat("yyyy");
                SimpleDateFormat month = new SimpleDateFormat("MM");
                SimpleDateFormat day = new SimpleDateFormat("dd");
                String str_year = year.format(date1);
                String str_month = month.format(date1);
                String str_day = day.format(date1);
                String place_i = info_place.getText().toString();
                String event_i = info_event.getText().toString();
                dbs.insert(str_year,str_month,str_day,place_i,event_i,lat_record,longi_record);
            }
        });
    }
}
