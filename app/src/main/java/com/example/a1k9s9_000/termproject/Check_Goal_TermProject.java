package com.example.a1k9s9_000.termproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorJoiner;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

public class Check_Goal_TermProject extends AppCompatActivity {
    TextView title_achievement, term, distance, calorie, date1, date2, goal_d, real_d, achieve_d, goal_c, real_c, achieve_c;
    AlertDialog.Builder builder1;
    AlertDialog.Builder builder2;
    int kg_index;
    String goal_dis, goal_cal,year_1, month_1,day_1,year_2,month_2,day_2,set_date_1,set_date_2;
    int add;
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

        // 사용자가 목표 설정한 데이터를 기반으로 한 선택 메뉴
        DataBase_Goal_TermProject dgt = new DataBase_Goal_TermProject(getApplicationContext(), "List_control_goal.db",null,1);
        ArrayList<CharSequence> select = dgt.getResult_select_menu();
        final CharSequence[] item_select = select.toArray(new String[select.size()]);
        builder2 = new AlertDialog.Builder(this);

        builder2.setTitle("○다음 중 어떤 목표의 달성 여부를 확인하시겠습니까?○")
                .setItems(item_select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        add = which + 1;
                        String str_which = String.valueOf(add); // 데이터 베이스의 정보를 가져오기 위한 스트링

                        // 데이터 베이스를 불러오기 위한 객체
                        DataBase_Goal_TermProject db = new DataBase_Goal_TermProject(getApplicationContext(), "List_control_goal.db", null, 1);
                        DataBase_s_TermProject dbs = new DataBase_s_TermProject(getApplicationContext(), "List_statistic.db",null,1);

                        // DataBase_Goal_TermProject에서 가져온 데이터
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

                        date1.setText(set_date_1); // 목표 기간 1 setting
                        date2.setText(set_date_2); // 목표 기간 2 setting

                        String set_goal_d = goal_dis + "M ->";
                        String set_goal_c = goal_cal + " -> ";

                        goal_d.setText(set_goal_d); // 목표한 거리 수 setting
                        goal_c.setText(set_goal_c); // 목표한 칼로리 수 setting

                        // 목표한 거리 Double형으로 바꿔주기. 달성 여부 확인을 위함
                        String dis_temp1 = goal_dis;
                        dis_temp1 = dis_temp1.trim();
                        double goal_distance_int = !dis_temp1.equals("") ? Double.parseDouble(dis_temp1) : 0.0;

                        // 목표한 칼로리 Double형으로 바꿔주기. 달성 여부 확인을 위함
                        String cal_temp1 = goal_cal;
                        cal_temp1 = cal_temp1.trim();
                        Double goal_calorie_double = !cal_temp1.equals("") ? Double.parseDouble(cal_temp1) : 0.0;

                        // 움직인 거리 계산
                        double sum_distance = sum_distance_function(year_1, month_1, day_1, day_2);
                        String final_dis = String.valueOf(sum_distance);
                        String format = final_dis + "M";

                        real_d.setText(format); // 실제 움직인 거리

                        // 움직인 거리가 목표보다 높으면 "달성 완료!"
                        if (goal_distance_int <= sum_distance)
                            achieve_d.setText("달성 완료!");
                        // 움직인 거리가 목표보다 작으면 "달성 실패!"
                        else
                            achieve_d.setText("달성 실패!");

                        // 움직임으로 인한 칼로리 계산
                        double calculate_cal = calculate_calorie(kg_index, sum_distance);
                        String str_calculate_cal = String.valueOf(calculate_cal);
                        String format_c = str_calculate_cal + "kcal";

                        // 실제 칼로리 소모량
                        real_c.setText(format_c);

                        // 칼로리 소모량이 목표량 보다 높으면 "달성 완료!"
                        if (goal_calorie_double <= calculate_cal)
                            achieve_c.setText("달성 완료!");
                        // 칼로리 소모량이 목표량 보다 낮으면 "달성 실패!"
                        else
                            achieve_c.setText("달성 실패!");
                    }
                });
        AlertDialog ad1 = builder2.create();
        ad1.show();

        // 칼로리 계산을 위한 몸무게 선택 메뉴
        final CharSequence[] items = {"55kg 이하","55~65kg", "65~75kg","75~85kg","85kg 이상"};
        builder1 = new AlertDialog.Builder(this);

        builder1.setTitle("◆몸 무 게 설 정◆");
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

    // 목표 기간 동안 걸어 다닌 거리 계산 함수
    public double sum_distance_function(String year1, String month1, String day1, String day2) {

        // 데이터 베이스 함수 호출을 위한 데이터 베이스 객체
        DataBase_s_TermProject db = new DataBase_s_TermProject(getApplicationContext(),"List_statistic.db",null,1);

        double sum = 0;

        String day1_temp1 = day1;
        day1_temp1 = day1_temp1.trim();
        int temp_day1 = !day1_temp1.equals("")?Integer.parseInt(day1_temp1):0;

        String day2_temp1 = day2;
        day2_temp1 = day2_temp1.trim();
        int temp_day2 = !day2_temp1.equals("")?Integer.parseInt(day2_temp1):0;

        while(temp_day1 <= temp_day2) {
            String str_temp_day = String.valueOf(temp_day1);
            String temp_d = db.getResult_distance(year1,month1,str_temp_day);
            temp_d = temp_d.trim();
            double temp_distance_int = !temp_d.equals("")?Double.parseDouble(temp_d):0.0;
            sum += temp_distance_int;
            temp_day1++;
        }
        return sum;
    }

    // 몸무게에 따른 칼로리 계산 함수
    public double calculate_calorie(int index, double d) {
        double final_cal = 0.0;

        if(index == 0) {
            double temp = d* 0.022;
            String temp_str = String.format("%.2f",temp);
            final_cal = Double.parseDouble(temp_str);
        }

        else if(index == 1) {
            double temp = d* 0.027;
            String temp_str = String.format("%.2f",temp);
            final_cal = Double.parseDouble(temp_str);
        }

        else if(index == 2) {
            double temp = d* 0.032;
            String temp_str = String.format("%.2f",temp);
            final_cal = Double.parseDouble(temp_str);
        }

        else if(index == 3) {
            double temp = d* 0.037;
            String temp_str = String.format("%.2f",temp);
            final_cal = Double.parseDouble(temp_str);
        }

        else if(index == 4) {
            double temp = d* 0.042;
            String temp_str = String.format("%.2f",temp);
            final_cal = Double.parseDouble(temp_str);
        }
        return final_cal;
    }
}
