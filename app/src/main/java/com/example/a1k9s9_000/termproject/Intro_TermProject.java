package com.example.a1k9s9_000.termproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Intro_TermProject extends AppCompatActivity {
    TextView title;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro__term_project);

        title = (TextView)findViewById(R.id.introtitle);
        handler = new Handler();
        handler.postDelayed(introrun, 1500);
    }

    Runnable introrun = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), Main_TermProject.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(introrun);
    }
}
