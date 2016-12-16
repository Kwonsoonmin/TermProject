package com.example.a1k9s9_000.termproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Goal_TermProject extends AppCompatActivity {
    TextView term, distance, calorie;
    Button setting, check_goal;
    EditText year1, month1, day1,year2,month2,day2, set_d,set_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal__term_project);

        // 목표 기간 설정에 대한 알림창 띄우기
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("♠알림♠")
                .setMessage("목표 설정 가능 기간은 최대 14일입니다. 14일 이내의 목표 기간을 잡아주세요.")
                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

        term = (TextView)findViewById(R.id.term_ac);
        distance = (TextView)findViewById(R.id.distance_ac);
        calorie = (TextView)findViewById(R.id.calorie_ac);

        setting = (Button)findViewById(R.id.setting);
        check_goal = (Button)findViewById(R.id.check_goal);

        year1 = (EditText)findViewById(R.id.year1);
        month1 = (EditText)findViewById(R.id.month1);
        day1 = (EditText)findViewById(R.id.day1);
        year2 = (EditText)findViewById(R.id.year2);
        month2 = (EditText)findViewById(R.id.month2);
        day2 = (EditText)findViewById(R.id.day2);
        set_d = (EditText)findViewById(R.id.set_d);
        set_c = (EditText)findViewById(R.id.set_c);

        // 목표 관리하는 데이터 베이스에 기록한 내용 삽입
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"등록되었습니다. 목표 달성을 위해 힘내주세요 :)",Toast.LENGTH_SHORT).show();

                DataBase_Goal_TermProject dbt = new DataBase_Goal_TermProject(getApplicationContext(), "List_control_goal.db",null, 1);

                String first_year = year1.getText().toString();
                String first_month = month1.getText().toString();
                String first_day = day1.getText().toString();

                String second_year = year2.getText().toString();
                String second_month = month2.getText().toString();
                String second_day = day2.getText().toString();

                String set_distance = set_d.getText().toString();
                String set_calorie = set_c.getText().toString();

                dbt.insert(first_year,first_month,first_day,second_year,second_month,second_day,set_distance,set_calorie);
            }
        });

        // 달성 여부 확인 화면으로 이동
        check_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 화면 전환을 위한 intent
                Intent intent2 = new Intent(getApplicationContext(), Check_Goal_TermProject.class);
                startActivity(intent2);
            }
        });
    }
}
