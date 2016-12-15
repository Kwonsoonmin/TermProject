package com.example.a1k9s9_000.termproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Show_Data_TermProject extends AppCompatActivity {
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__data__term_project);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("♠알림♠")
                .setMessage("통계 내용이 보이지 않을 경우, 통계 데이터가 존재하지 않는 것입니다."+"\n" +"통계가 보이지 않으면 뒤로 돌아가주세요.")
                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

        result = (TextView)findViewById(R.id.result);

        Intent intent = getIntent();

        String g_index = intent.getStringExtra("index");
        int g_index_i = Integer.parseInt(g_index);

        String g_year = intent.getStringExtra("year");
        String g_month = intent.getStringExtra("month");
        String g_day = intent.getStringExtra("day");
        String g_temp1 = intent.getStringExtra("tempday1");
        String g_temp2 = intent.getStringExtra("tempday2");
        String g_temp3 = intent.getStringExtra("tempday3");
        String g_temp4 = intent.getStringExtra("tempday4");
        String g_temp5 = intent.getStringExtra("tempday5");
        String g_temp6 = intent.getStringExtra("tempday6");


        DataBase_s_TermProject db = new DataBase_s_TermProject(getApplicationContext(), "List_statistics.db",null,1);

        if(g_index_i == 0) {
            result.setText(db.getResult_oneday(g_year,g_month,g_day));
        }

        else if(g_index_i == 1) {
            result.setText(db.getResult_three(g_year,g_month,g_day,g_temp1,g_temp2));
        }

        else if(g_index_i == 2) {
            result.setText(db.getResult_oneweek(g_year, g_month,g_day,g_temp1,g_temp2,g_temp3,g_temp4,g_temp5,g_temp6));
        }

        else
            result.setText(db.getResult());
    }
}
