package com.example.a1k9s9_000.termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_TermProject extends AppCompatActivity {
    TextView title;
    Button event, goal, statistic, facilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__term_project);

        event = (Button)findViewById(R.id.month);
        goal = (Button)findViewById(R.id.goal);
        statistic = (Button)findViewById(R.id.statistic);
        facilities = (Button)findViewById(R.id.facilities);

        // 이벤트 기록 화면으로 전환
        event.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Event_TermProject.class);
                startActivity(intent);
            }
        });

        // 목표 관리 화면으로 전환
        goal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Goal_TermProject.class);
                startActivity(intent);
            }
        });

        // 통계를 볼 수 있는 화면으로 전환
        statistic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Statistic_TermProject.class);
                startActivity(intent);
            }
        });

        // 편의시설 정보를 볼 수 있는 화면으로 전환
        facilities.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Facilities_TermProject.class);
                startActivity(intent);
            }
        });
    }
}
