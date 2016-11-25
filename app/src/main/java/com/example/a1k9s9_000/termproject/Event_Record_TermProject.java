package com.example.a1k9s9_000.termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        record_title = (TextView)findViewById(R.id.record_title);
        place = (TextView)findViewById(R.id.place);
        event = (TextView)findViewById(R.id.event);
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

        String lat_record = intent.getStringExtra("lat");
        String longi_record = intent.getStringExtra("long");

        info_lat.setText(lat_record);
        info_longi.setText(longi_record);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy년 MM월 dd일");
        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");

        String str_dateformat = dateformat.format(date);
        String str_timeformat = timeformat.format(date);

        info_time.setText(str_timeformat);
        info_date.setText(str_dateformat);

    }
}
