package com.example.a1k9s9_000.termproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorJoiner;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Check_Goal_TermProject extends AppCompatActivity {
    TextView title_achievement, term, distance, calorie, date1, date2, goal_d, real_d, achieve_d, goal_c, real_c, achieve_c;
    AlertDialog.Builder builder1;
    AlertDialog.Builder builder2;
    int kg_index;
    String goal_dis, goal_cal,year_1, month_1,day_1,year_2,month_2,day_2,set_date_1,set_date_2;
    //int sum_distance = 0;
    //int temp_day1, temp_day2, goal_distance_int;
    //Double goal_calorie_double;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__goal__term_project);

        title_achievement = (TextView)findViewById(R.id.title_achievement);
        term = (TextView)findViewById(R.id.term_ac);
        distance = (TextView)findViewById(R.id.distance_ac);
        calorie = (TextView)findViewById(R.id.calorie_ac);
        date1 = (TextView)findViewById(R.id.date1);
        date2 = (TextView)findViewById(R.id.date2);
        goal_d = (TextView)findViewById(R.id.goal_d);
        real_d = (TextView)findViewById(R.id.real_d);
        achieve_d = (TextView)findViewById(R.id.achieve_d);
        goal_c = (TextView)findViewById(R.id.goal_c);
        real_c = (TextView)findViewById(R.id.real_c);
        achieve_c = (TextView)findViewById(R.id.achieve_c);

        DataBase_Goal_TermProject dgt = new DataBase_Goal_TermProject(getApplicationContext(), "List_control_goal.db",null,1);
        ArrayList<CharSequence> select = dgt.getResult_select_menu();
        final CharSequence[] item_select = select.toArray(new String[select.size()]);
        builder2 = new AlertDialog.Builder(this);

        builder2.setTitle("○다음 중 어떤 목표의 달성 여부를 확인하시겠습니까?○")
                .setItems(item_select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int sum_distance = 0;
                        int add = which + 1;
                        String str_which = String.valueOf(add);

                        DataBase_Goal_TermProject db = new DataBase_Goal_TermProject(getApplicationContext(), "List_control_goal.db", null, 1);

                        goal_dis = db.getResult_distance(str_which);
                        goal_cal = db.getResult_calorie(str_which);
                        year_1 = db.getResult_year1(str_which);
                        year_2 = db.getResult_year2(str_which);
                        day_1 = db.getResult_day1(str_which);
                        day_2 = db.getResult_day2(str_which);
                        month_1 = db.getResult_month1(str_which);
                        month_2 = db.getResult_month2(str_which);
                        set_date_1 = db.getResult_date1(str_which);
                        set_date_2 = db.getResult_date2(str_which);
                        date1.setText(set_date_1); // 목표 기간 1
                        date2.setText(set_date_2); // 목표 기간 2

                        String set_goal_d = goal_dis + " -> ";
                        String set_goal_c = goal_cal + " -> ";

                        goal_d.setText(set_goal_d); // 목표한 거리 수
                        goal_c.setText(set_goal_c); // 목표한 칼로리 수

                        // 목표한 거리 int형으로 바꿔주기. 달성 여부 확인을 위함
                        String dis_temp1 = goal_dis;
                        dis_temp1 = dis_temp1.trim();
                        int goal_distance_int = !dis_temp1.equals("") ? Integer.parseInt(dis_temp1) : 0;

                        // 목표한 칼로리 int으로 바꿔주기. 달성 여부 확인을 위함
                        String cal_temp1 = goal_cal;
                        cal_temp1 = cal_temp1.trim();
                        Double goal_calorie_double = !cal_temp1.equals("") ? Double.parseDouble(cal_temp1) : 0.0;

                        sum_distance = sum_distance_function(year_1, month_1, day_1, day_2);
                        String final_dis = String.valueOf(sum_distance);
                        String format = final_dis + "M";

                        real_d.setText(format);

                        if (goal_distance_int <= sum_distance)
                            achieve_d.setText("달성 완료!");
                        else
                            achieve_d.setText("달성 실패!");

                        double calculate_cal = calculate_calorie(kg_index, sum_distance);
                        String str_calculate_cal = String.valueOf(calculate_cal);
                        String format_c = str_calculate_cal + "kcal";

                        real_c.setText(format_c);

                        if (goal_calorie_double <= calculate_cal)
                            achieve_c.setText("달성 완료!");
                        else
                            achieve_c.setText("달성 실패!");

                        dialog.dismiss();
                    }
                });
        AlertDialog ad1 = builder2.create();
        ad1.show();

        final CharSequence[] items = {"55kg 이하","55~65kg", "65~75kg","75~85kg","85kg 이상"};
        builder1 = new AlertDialog.Builder(this);

        builder1.setTitle("◆몸 무 게 설 정◆"); // 칼로리 계산을 위한 몸무게 선택 메뉴.
        builder1.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        kg_index = 0;
                        dialog.dismiss();
                        break;

                    case 1:
                        kg_index = 1;
                        dialog.dismiss();
                        break;

                    case 2:
                        kg_index = 2;
                        dialog.dismiss();
                        break;

                    case 3:
                        kg_index = 3;
                        dialog.dismiss();
                        break;

                    case 4:
                        kg_index = 4;
                        dialog.dismiss();
                        break;
                }
            }
        });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

    public int sum_distance_function(String year1, String month1, String day1, String day2) {
        DataBase_s_TermProject db = new DataBase_s_TermProject(getApplicationContext(),"List_statistic",null,1);

        int sum = 0;

        String day1_temp1 = day1;
        day1_temp1 = day1_temp1.trim();
        int temp_day1 = !day1_temp1.equals("")?Integer.parseInt(day1_temp1):0;

        String day2_temp1 = day2;
        day2_temp1 = day2_temp1.trim();
        int temp_day2 = !day2_temp1.equals("")?Integer.parseInt(day2_temp1):0;

        while(temp_day1 <= temp_day2) {
            String str_temp_day = String.valueOf(temp_day1);
            String temp_distace = db.getResult_distance(year_1,month_1,str_temp_day);
            temp_distace = temp_distace.trim();
            int temp_distance_int = !temp_distace.equals("")?Integer.parseInt(temp_distace):0;
            sum += temp_distance_int;
            temp_day1++;
        }

        return sum;
    }
    public double calculate_calorie(int index, int d) {
        double final_cal = 0.0;

        if(index == 0) {
            final_cal = d * 2.2;
        }

        else if(index == 1) {
            final_cal = d * 2.7;
        }

        else if(index == 2) {
            final_cal = d * 3.2;
        }

        else if(index == 3) {
            final_cal = d * 3.7;
        }

        else if(index == 4) {
            final_cal = d* 4.2;
        }

        return final_cal;
    }
}
