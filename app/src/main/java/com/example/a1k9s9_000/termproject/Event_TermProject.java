package com.example.a1k9s9_000.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.util.LinkedList;

public class Event_TermProject extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback{
    private TMapGpsManager tmapgps = null;
    private Context mContext = null;
    private boolean m_bTrackingMode = true;
    private TMapView tMapView = null;
    private static int markerid;
    int count = 0;

    private LinkedList<TMapPoint> tmappoint_linked = new LinkedList<TMapPoint>();
    private LinkedList<String> markerid_linked = new LinkedList<String>();
    private LinkedList<MapPoint> mapppoint_linked = new LinkedList<MapPoint>();

    public void onLocationChange(Location location) {
        if(m_bTrackingMode) {
            tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__term_project);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("♠알림♠")
                .setMessage("마커를 찍고, 기록하는 동안에는 계속 이 화면에 머물러주세요."+"\n"+"자, 그럼 오늘 하루 일정을 기록해주세요!")
                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();


        mContext = this;

        Button marker = (Button)findViewById(R.id.marker);

        marker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CurrentLocation cl = new CurrentLocation(Event_TermProject.this);
                double latitude = 0;
                double longitude = 0;

                if(cl.isGetLocation()) {
                    latitude = cl.getLat();
                    longitude = cl.getLongi();

                    Toast.makeText(Event_TermProject.this, "Complete Marking. Write Event", Toast.LENGTH_SHORT).show();
                    addpoint(latitude, longitude);
                    showmarkerpoint(count);
                }

                count++;
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.map_view);
        tMapView = new TMapView(this);

        linearLayout.addView(tMapView);
        tMapView.setSKPMapApiKey("d3fa73ac-2d6a-3a13-bee0-39a0de4934b5");

        tMapView.setCompassMode(true);

        tMapView.setIconVisibility(true);

        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        tmapgps = new TMapGpsManager(Event_TermProject.this);
        tmapgps.setMinTime(1000);
        tmapgps.setMinDistance(2);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER);
        //tmapgps.setProvider(tmapgps.GPS_PROVIDER);
        tmapgps.OpenGps();

        tMapView.setTrackingMode(true);
        tMapView.setSightVisible(true);

        tMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem tMapMarkerItem) {
                CurrentLocation cl1 = new CurrentLocation(Event_TermProject.this);
                double lat_i = 0;
                double longi_i = 0;
                String lat_i_s = "";
                String longi_i_s = "";

                if(cl1.isGetLocation()) {
                    lat_i = cl1.getLat();
                    longi_i = cl1.getLongi();

                    lat_i_s = String.valueOf(lat_i);
                    longi_i_s = String.valueOf(longi_i);

                    Intent intent1 = new Intent(getApplicationContext(), Event_Record_TermProject.class);
                    intent1.putExtra("lat", lat_i_s);
                    intent1.putExtra("long", longi_i_s);
                    startActivity(intent1);
                }
            }
        });
    }

    public void addpoint(double lat, double longi) {
        mapppoint_linked.add(new MapPoint("What are you doing?", lat, longi));
    }

    public void showmarkerpoint(int count) {
        TMapPoint point = new TMapPoint(mapppoint_linked.get(count).getLatitude(), mapppoint_linked.get(count).getLongitude());
        TMapMarkerItem item1 = new TMapMarkerItem();
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.poi_dot);

        item1.setTMapPoint(point);
        item1.setName(mapppoint_linked.get(count).getName());
        item1.setVisible(item1.VISIBLE);
        item1.setIcon(bitmap);

        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.poi_dot);

        item1.setCalloutTitle((mapppoint_linked.get(count).getName()));
        item1.setCanShowCallout(true);
        item1.setAutoCalloutVisible(true);

        Bitmap bitmapigo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.i_go);
        item1.setCalloutRightButtonImage(bitmapigo);


        String strid = String.format("marker%d", markerid++);

        tMapView.addMarkerItem(strid, item1);
        markerid_linked.add(strid);
    }
}
