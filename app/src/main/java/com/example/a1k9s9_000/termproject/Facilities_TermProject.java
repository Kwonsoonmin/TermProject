package com.example.a1k9s9_000.termproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Facilities_TermProject extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback{
    ScrollView scrollView;
    TextView information;
    Button showmarker;
    private Context mContext = null;
    private boolean m_bTeackingMode = true;

    private TMapData tMapdata = null;
    private TMapGpsManager tmapgps = null;
    private TMapView tMapView = null;
    private static String apikey = "d3fa73ac-2d6a-3a13-bee0-39a0de4934b5";

    AlertDialog.Builder builder;

    int number = 0;

    public void onLocationChange(Location lo) {
        if(m_bTeackingMode) {
            tMapView.setLocationPoint(lo.getLongitude(), lo.getLatitude());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities__term_project);
        scrollView = (ScrollView)findViewById(R.id.scroll);
        information = (TextView)findViewById(R.id.information_f);
        showmarker = (Button)findViewById(R.id.showmarker);

        progress p = new progress();
        p.execute();

        final CharSequence[] items = {"병원 및 약국","편의점","은행","화장실","관공서","음식점","카페","문화시설","지하철","주유소"};
        builder = new AlertDialog.Builder(this);

        builder.setTitle("※편의시설※")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:
                                getFacilitiesData_hospital();
                                number = 1;
                                 break;

                            case 1:
                                getFacilitiesData_convenience();
                                number = 2;
                                break;

                            case 2:
                                getFacilitiesData_bank();
                                number = 3;
                                break;

                            case 3:
                                getFacilitiesData_toilet();
                                number = 4;
                                break;

                            case 4:
                                getFacilitiesData_government_office();
                                number = 5;
                                break;

                            case 5:
                                getFacilitiesData_food();
                                number = 6;
                                break;

                            case 6:
                                getFacilitiesData_coffee();
                                number = 7;
                                break;

                            case 7:
                                getFacilitiesData_culture();
                                number = 8;
                                break;

                            case 8:
                                getFacilitiesData_subway();
                                number = 9;
                                break;

                            case 9:
                                getFacilitiesData_gasstation();
                                number = 10;
                                break;
                        }
                    }
                });

        mContext = this;

        tMapdata = new TMapData();
        tMapView = new TMapView(this);
        tMapView.setSKPMapApiKey(apikey);

        tMapView.setCompassMode(true);
        tMapView.setIconVisibility(true);
        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        tmapgps = new TMapGpsManager(Facilities_TermProject.this);
        tmapgps.setMinTime(1000);
        tmapgps.setMinDistance(2);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER);
        //tmapgps.setProvider(tmapgps.GPS_PROVIDER);
        tmapgps.OpenGps();

        tMapView.setTrackingMode(true);
        tMapView.setSightVisible(true);

        showmarker.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Facilities_with_Map_TermsProject.class);

                if(number == 1)
                    intent.putExtra("숫자",1);
                else if(number == 2)
                    intent.putExtra("숫자",2);
                else if(number == 2)
                    intent.putExtra("숫자",2);
                else if(number == 3)
                    intent.putExtra("숫자",3);
                else if(number == 4)
                    intent.putExtra("숫자",4);
                else if(number == 5)
                    intent.putExtra("숫자",5);
                else if(number == 6)
                    intent.putExtra("숫자",6);
                else if(number == 7)
                    intent.putExtra("숫자",7);
                else if(number == 8)
                    intent.putExtra("숫자",8);
                else if(number == 9)
                    intent.putExtra("숫자",9);
                else if(number == 10)
                    intent.putExtra("숫자",10);
                startActivity(intent);
            }
        });
    }

    public void getFacilitiesData_hospital() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "약국",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s1 = "";
                                String text="";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s1 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() + ", 근방 "+dis+"M"+"\n";
                                    }
                                    information.setText(s1);
                                }catch(NullPointerException ne){
                                    text = "주변에 병원 및 약국 이 없습니다.";
                                    information.setText(text);
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_convenience() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "편의점",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s2 = "";
                                String text1 = "";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s2 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() + ", 근방 "+dis+"M"+"\n";
                                    }
                                    information.setText(s2);
                                }catch (NullPointerException ne){
                                    text1 = "주변에 편의점이 없습니다.";
                                    information.setText(text1);
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_bank() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "은행;ATM",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s3 = "";
                                String text2="";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f", item.getDistance(tp));
                                        s3 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() +", 근방 "+dis+"M"+ "\n";
                                    }
                                    information.setText(s3);
                                }catch (NullPointerException ne){
                                    text2 = "주변에 은행, ATM이 없습니다.";
                                    information.setText(text2);
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_toilet() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "화장실",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s1 = "";
                                String text="";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s1 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() + ", 근방 "+dis+"M"+"\n";
                                    }
                                    information.setText(s1);
                                }catch(NullPointerException ne){
                                    text = "주변에 화장실이 없습니다.";
                                    information.setText(text);
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_government_office() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "관공서",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s1 = "";
                                String text="";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s1 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() + ", 근방 "+dis+"M"+"\n";
                                    }
                                    information.setText(s1);
                                }catch(NullPointerException ne){
                                    text = "주변에 관공서가 없습니다.";
                                    information.setText(text);
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_food() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "음식",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s1 = "";
                                String text="";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s1 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() + ", 근방 "+dis+"M"+"\n";
                                    }
                                    information.setText(s1);
                                }catch(NullPointerException ne){
                                    text = "주변에 음식점이 없습니다.";
                                    information.setText(text);
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_coffee() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "카페",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s1 = "";
                                String text="";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s1 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() + ", 근방 "+dis+"M"+"\n";
                                    }
                                    information.setText(s1);
                                }catch(NullPointerException ne){
                                    text = "주변에 카페가 없습니다.";
                                    information.setText(text);
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_culture() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "문화시설;영화관;노래방;PC방;스크린골프장",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s1 = "";
                                String text="";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s1 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() + ", 근방 "+dis+"M"+"\n";
                                    }
                                    information.setText(s1);
                                }catch(NullPointerException ne){
                                    text = "주변에 문화시설이 없습니다.";
                                    information.setText(text);
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_subway() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "지하철;",2,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s5 = "";
                                String text4 = "";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s5 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() +". 근방 "+dis+"M"+ "\n";
                                    }
                                    information.setText(s5);
                                }catch (NullPointerException ne){
                                    text4 = "주변에 지하철역이 없습니다.";
                                    information.setText(text4);
                                }
                            }
                        });
                    }});
    }

    public void getFacilitiesData_gasstation() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapData.findAroundNamePOI(point, "주유소",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s1 = "";
                                String text="";
                                String dis = "";
                                TMapPoint tp = tMapView.getCenterPoint();
                                try {
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        dis = String.format("%.2f",item.getDistance(tp));
                                        s1 += item.getPOIAddress().replace("null", "") + "-> " + item.getPOIName() + ", 근방 "+dis+"M"+"\n";
                                    }
                                    information.setText(s1);
                                }catch(NullPointerException ne){
                                    text = "주변에 주유소가 없습니다.";
                                    information.setText(text);
                                }
                            }
                        });
                    }
                });
    }

    private class progress extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd = new ProgressDialog(Facilities_TermProject.this);

        protected void onPreExecute() {
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("현재 위치를 찾는 중입니다. 조금만 기다려 주세요.");

            pd.show();
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            }catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            pd.dismiss();
            AlertDialog dialog = builder.create();
            dialog.show();
            super.onPostExecute(result);
        }
    }
}

