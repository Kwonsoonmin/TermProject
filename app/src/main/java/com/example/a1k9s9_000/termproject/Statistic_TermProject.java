package com.example.a1k9s9_000.termproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;

public class Statistic_TermProject extends AppCompatActivity {
    TextView year_l,month_l,day_l;
    EditText year_i, month_i, day_i;
    Button show;
    int index;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic__term_project);

        year_l = (TextView)findViewById(R.id.year);
        month_l= (TextView)findViewById(R.id.month);
        day_l = (TextView)findViewById(R.id.day);
        year_i = (EditText)findViewById(R.id.info_year);
        month_i = (EditText)findViewById(R.id.info_month);
        day_i = (EditText)findViewById(R.id.info_day);
        show = (Button)findViewById(R.id.show);

        final Context context = this;
        final CharSequence[] items = {"하루 통계", "3일 통계", "일주일 통계", "모든 통계"};
        final DataBase_s_TermProject database_s =  new DataBase_s_TermProject(getApplicationContext(), "List_statistics.db",null,1);
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(context);

        long now = System.currentTimeMillis();
        Date d = new Date(now);

        alertdialogbuilder.setTitle("보고 싶은 통계를 선택하세요.");
        alertdialogbuilder.setItems(items,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i) {
                            case 0:
                                index = 0;
                                progress p = new progress();
                                p.execute();
                                Toast.makeText(getApplicationContext(), items[0]+"를 선택했습니다.",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                break;
                            case 1:
                                index = 1;
                                progress p1 = new progress();
                                p1.execute();
                                Toast.makeText(getApplicationContext(), items[1]+"를 선택했습니다.",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                break;
                            case 2:
                                index = 2;
                                progress p2 = new progress();
                                p2.execute();
                                Toast.makeText(getApplicationContext(), items[2]+"를 선택했습니다.",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                break;
                            case 3:
                                index = 3;
                                progress p3 = new progress();
                                p3.execute();
                                Toast.makeText(getApplicationContext(), items[3]+"를 선택했습니다.",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                                break;
                        }
                    }
                });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();

        final String year_data = year_i.getText().toString();
        final String month_data = month_i.getText().toString();
        final String day_data = day_i.getText().toString();

        String day_temp = day_data;

        day_temp = day_temp.trim();
        int temp0 = !day_temp.equals("")?Integer.parseInt(day_temp):0;
        int temp1 = temp0 + 1;
        int temp2 = temp0 + 2;
        int temp3 = temp0 + 3;
        int temp4 = temp0 + 4;
        int temp5 = temp0 + 5;
        int temp6 = temp0 + 6;

        final String day_temp1 = String.valueOf(temp1);
        final String day_temp2 = String.valueOf(temp2);
        final String day_temp3 = String.valueOf(temp3);
        final String day_temp4 = String.valueOf(temp4);
        final String day_temp5 = String.valueOf(temp5);
        final String day_temp6 = String.valueOf(temp6);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(index){
                    case 0:
                        String index_s= toString().valueOf(index);
                        Intent intent = new Intent(getApplicationContext(), Show_Data_TermProject.class);
                        intent.putExtra("year",year_data);
                        intent.putExtra("month", month_data);
                        intent.putExtra("day", day_data);
                        intent.putExtra("index",index_s);
                        startActivity(intent);
                        break;

                    case 1:
                        String index_s1= toString().valueOf(index);
                        Intent intent1 = new Intent(getApplicationContext(), Show_Data_TermProject.class);
                        intent1.putExtra("year",year_data);
                        intent1.putExtra("month", month_data);
                        intent1.putExtra("day", day_data);
                        intent1.putExtra("index",index_s1);
                        intent1.putExtra("tempday1",day_temp1);
                        intent1.putExtra("tempday2",day_temp2);
                        startActivity(intent1);
                        break;

                    case 2:
                        String index_s2= toString().valueOf(index);
                        Intent intent2 = new Intent(getApplicationContext(), Show_Data_TermProject.class);
                        intent2.putExtra("year",year_data);
                        intent2.putExtra("month", month_data);
                        intent2.putExtra("day", day_data);
                        intent2.putExtra("index",index_s2);
                        intent2.putExtra("tempday1",day_temp1);
                        intent2.putExtra("tempday2",day_temp2);
                        intent2.putExtra("tempday3",day_temp3);
                        intent2.putExtra("tempday4",day_temp4);
                        intent2.putExtra("tempday5",day_temp5);
                        intent2.putExtra("tempday6",day_temp6);
                        startActivity(intent2);
                        break;

                    case 3:
                        String index_s3= toString().valueOf(index);
                        Intent intent3 = new Intent(getApplicationContext(), Show_Data_TermProject.class);
                        intent3.putExtra("index",index_s3);
                        startActivity(intent3);
                        break;
                }
            }
        });
    }

    private class progress extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd = new ProgressDialog(Statistic_TermProject.this);

        protected void onPreExecute() {
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            switch(index) {
                case 0:
                    pd.setMessage("찾고 싶은 날짜를 입력하세요. 그 날 하루의 통계가 보여집니다.");

                    pd.show();
                    super.onPreExecute();
                    break;

                case 1:
                    pd.setMessage("시작 날짜를 입력하세요. 입력한 날짜부터 3일 간의 통계가 보여집니다.");

                    pd.show();
                    super.onPreExecute();
                    break;

                case 2:
                    pd.setMessage("시작 날짜를 입력하세요. 입력한 날짜부터 1주일 간의 통계가 보여집니다.");

                    pd.show();
                    super.onPreExecute();
                    break;

                case 3:
                    pd.setMessage("입력 날짜 없이 버튼을 눌러주세요. 모든 통계가 보여집니다.");

                    pd.show();
                    super.onPreExecute();
                    break;
            }
        }

        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            }catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            pd.dismiss();
            super.onPostExecute(result);
        }
    }
}
