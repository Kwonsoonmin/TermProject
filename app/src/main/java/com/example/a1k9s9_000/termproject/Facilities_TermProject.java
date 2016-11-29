package com.example.a1k9s9_000.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private Context mContext = null;
    private boolean m_bTeackingMode = true;

    private TMapData tMapdata = null;
    private TMapGpsManager tmapgps = null;
    private TMapView tMapView = null;
    private static String apikey = "d3fa73ac-2d6a-3a13-bee0-39a0de4934b5";

    private String s1 = "";

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

        final CharSequence[] items = {"병원","편의점","은행","버스","지하철"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("※편의시설※")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:
                                 information.setText(getFacilitiesData_hospital());
                                 break;

                            case 1:
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getFacilitiesData_convenience();
                                    }
                                }).start();
                                break;

                            case 2:
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getFacilitiesData_bank();
                                    }
                                }).start();
                                break;

                            case 3:
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getFacilitiesData_bus();
                                    }
                                }).start();
                                break;

                            case 4:
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getFacilitiesData_subway();
                                    }
                                }).start();
                                break;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        mContext = this;

        tMapdata = new TMapData();
        tMapView = new TMapView(this);
        tMapView.setSKPMapApiKey(apikey);

        tmapgps = new TMapGpsManager(Facilities_TermProject.this);
        tmapgps.setMinTime(1000);
        tmapgps.setMinDistance(2);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER);
        tmapgps.OpenGps();
    }

    public String getFacilitiesData_hospital() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();
        ArrayList<TMapPOIItem> poi;

        tMapdata.findAroundNamePOI(point, "병원",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        for(int i  = 0; i < poiItem.size(); i++) {
                            TMapPOIItem item = poiItem.get(i);
                            s1+= item.getPOIAddress().replace("null","") +", "+item.getPOIName()+"\n";
                        }
                    }});
        return s1;
    }

    public void getFacilitiesData_convenience() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapdata.findAroundNamePOI(point, "편의점",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        String s = "";
                        for(int i  = 0; i < poiItem.size(); i++) {
                            TMapPOIItem item = poiItem.get(i);
                            s += item.getPOIAddress().replace("null","") +", "+item.getPOIName()+"\n";
                        }
                        information.setText(s);
                    }});
    }

    public void getFacilitiesData_bank() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapdata.findAroundNamePOI(point, "은행",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        String s = "";
                        for(int i  = 0; i < poiItem.size(); i++) {
                            TMapPOIItem item = poiItem.get(i);
                            s += item.getPOIAddress().replace("null","") +", "+item.getPOIName()+"\n";
                        }
                        information.setText(s);
                    }});
    }

    public void getFacilitiesData_bus() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapdata.findAroundNamePOI(point, "버스",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        String s = "";
                        for(int i  = 0; i < poiItem.size(); i++) {
                            TMapPOIItem item = poiItem.get(i);
                            s += item.getPOIAddress().replace("null","") +", "+item.getPOIName()+"\n";
                        }
                        information.setText(s);
                    }});
    }

    public void getFacilitiesData_subway() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tMapView.getCenterPoint();

        tMapdata.findAroundNamePOI(point, "지하철",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        String s = "";
                        for(int i  = 0; i < poiItem.size(); i++) {
                            TMapPOIItem item = poiItem.get(i);
                            s += item.getPOIAddress().replace("null","") +", "+item.getPOIName()+"\n";
                        }
                        information.setText(s);
                    }});
    }
}
