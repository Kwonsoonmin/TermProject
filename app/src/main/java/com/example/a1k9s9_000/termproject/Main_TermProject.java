package com.example.a1k9s9_000.termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_TermProject extends AppCompatActivity {
    TextView title;
    Button introduce, event, goal, statistic, facilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__term_project);

        introduce = (Button)findViewById(R.id.introduce);
        event = (Button)findViewById(R.id.event);
        goal = (Button)findViewById(R.id.goal);
        statistic = (Button)findViewById(R.id.statistic);
        facilities = (Button)findViewById(R.id.facilities);

        introduce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Introduce_TermProject.class);
                startActivity(intent);
            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Event_TermProject.class);
                startActivity(intent);
            }
        });

        goal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Goal_TermProject.class);
                startActivity(intent);
            }
        });

        statistic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Statistic_TermProject.class);
                startActivity(intent);
            }
        });

        facilities.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Facilities_TermProject.class);
                startActivity(intent);
            }
        });
    }
}
