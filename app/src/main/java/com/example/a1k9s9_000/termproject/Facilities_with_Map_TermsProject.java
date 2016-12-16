package com.example.a1k9s9_000.termproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

// 앞에서 리스트로 보여줬던 편의 시설 정보를 지도 위치 표시로 제공.
public class Facilities_with_Map_TermsProject extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    private Context context = null;
    private boolean m_bTrackingMode = true;

    private TMapGpsManager tmapgps = null;
    private TMapView tmapview = null;
    private static String mApiKey = "d3fa73ac-2d6a-3a13-bee0-39a0de4934b5";
    private static int mMarkserID;

    private ArrayList<TMapPoint> m_tmapPoint = new ArrayList<TMapPoint>();
    private ArrayList<String> mArrayMarkerID = new ArrayList<String>();
    private ArrayList<MapPoint>m_markerPoint = new ArrayList<MapPoint>();

    AlertDialog.Builder builder;

    public void onLocationChange(Location location) {
        if(m_bTrackingMode) {
            tmapview.setLocationPoint(location.getLongitude(), location.getLatitude());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities_with__map__terms_project);

        context = this;

        Button check = (Button)findViewById(R.id.check);

        LinearLayout linear = (LinearLayout) findViewById(R.id.mapview);
        tmapview = new TMapView(this);
        linear.addView(tmapview);
        tmapview.setSKPMapApiKey(mApiKey);

        progress p = new progress();
        p.execute();

        tmapview.setCompassMode(true);
        tmapview.setIconVisibility(true);

        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);

        tmapgps = new TMapGpsManager(Facilities_with_Map_TermsProject.this);
        tmapgps.setMinTime(1000);
        tmapgps.setMinDistance(2);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER);
        //tmapgps.setProvider(tmapgps.GPS_PROVIDER);
        tmapgps.OpenGps();

        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int number = intent.getIntExtra("숫자",0);

                switch(number) {
                    case 1:
                        getFacilitiesData_hospital();
                        break;

                    case 2:
                        getFacilitiesData_convenience();
                        break;

                    case 3:
                        getFacilitiesData_bank();
                        break;

                    case 4:
                        getFacilitiesData_toilet();
                        break;

                    case 5:
                        getFacilitiesData_government_office();
                        break;

                    case 6:
                        getFacilitiesData_food();
                        break;

                    case 7:
                        getFacilitiesData_coffee();
                        break;

                    case 8:
                        getFacilitiesData_culture();
                        break;

                    case 9:
                        getFacilitiesData_subway();
                        break;

                    case 10:
                        getFacilitiesData_gasstation();
                        break;
                }

            }
        });

        tmapview.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem markerItem) {
                Toast.makeText(Facilities_with_Map_TermsProject.this, "Show Information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // LinkedList에 마커 표시 될 장소 저장
    public void addPoint(ArrayList<TMapPOIItem> itemlist) {
        for(int i = 0; i < itemlist.size(); i++) {
            String name = itemlist.get(i).getPOIName();
            double latitude = itemlist.get(i).getPOIPoint().getLatitude();
            double longitude = itemlist.get(i).getPOIPoint().getLongitude();
            m_markerPoint.add(new MapPoint(name, latitude, longitude));
        }
    }

    // LikedList 내용을 기반으로 지도에 마커 표시
    public void showMarkerPoint() {
        for(int i = 0; i < m_markerPoint.size(); i++) {
            TMapPoint point = new TMapPoint(m_markerPoint.get(i).getLatitude(), m_markerPoint.get(i).getLongitude());
            TMapMarkerItem item1 = new TMapMarkerItem();
            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.poi_dot);

            item1.setTMapPoint(point);
            item1.setName(m_markerPoint.get(i).getName());
            item1.setVisible(item1.VISIBLE);

            item1.setIcon(bitmap);

            bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.poi_dot);

            item1.setCalloutTitle(m_markerPoint.get(i).getName());
            item1.setCanShowCallout(true);
            item1.setAutoCalloutVisible(true);

            Bitmap bitmap_i = BitmapFactory.decodeResource(context.getResources(), R.mipmap.i_go);

            item1.setCalloutRightButtonImage(bitmap_i);

            String strID = String.format("pmarker%d", mMarkserID++);

            tmapview.addMarkerItem(strID, item1);
            mArrayMarkerID.add(strID);
        }
    }

    public void getFacilitiesData_hospital() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "약국",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch(NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 병원 및 약국이 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_convenience() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "편의점",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch (NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 편의점이 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_bank() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "은행;ATM",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch (NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 은행, ATM이 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_toilet() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "화장실",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch(NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 화장실이 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_government_office() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "관공서",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch(NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 관공서가 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_food() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "음식",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch(NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 음식점이 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_coffee() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "카페",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch(NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 카페가 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_culture() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "문화시설;영화관;노래방;PC방;스크린골프장",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch(NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 문화시설이 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    public void getFacilitiesData_subway() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "지하철;",2,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch (NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 지하철역이 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }});
    }

    public void getFacilitiesData_gasstation() {
        TMapData tMapData = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();

        tMapData.findAroundNamePOI(point, "주유소",1,99,
                new TMapData.FindAroundNamePOIListenerCallback() {

                    @Override
                    public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> poiItem) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ArrayList<TMapPOIItem> temp = new ArrayList<TMapPOIItem>();
                                    for (int i = 0; i < poiItem.size(); i++) {
                                        TMapPOIItem item = poiItem.get(i);
                                        temp.add(item);
                                    }
                                    addPoint(temp);
                                    showMarkerPoint();
                                }catch(NullPointerException ne){
                                    AlertDialog alertDialog = new AlertDialog.Builder(Facilities_with_Map_TermsProject.this)
                                            .setTitle("♠알림♠")
                                            .setMessage("주변에 주유소가 없어 지도에 표시할 수 없습니다. \n이전 화면으로 돌아가 주세요.")
                                            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .show();
                                }
                            }
                        });
                    }
                });
    }

    private class progress extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd = new ProgressDialog(Facilities_with_Map_TermsProject.this);

        protected void onPreExecute() {
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("마킹 중입니다. \n마킹이 완료되면 '확인하기' 버튼을 눌러 위치를 확인하세요!");

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
            super.onPostExecute(result);
        }
    }
}
