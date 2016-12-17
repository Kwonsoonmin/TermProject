package com.example.a1k9s9_000.termproject;

/**
 * Created by 1k9s9_000 on 2016-11-24.
 */

// 지도에 저장하기 위한 객체를 생성하기 위해 만든 class
public class MapPoint {
    private String name;
    private double latitude;
    private double longitude;

    // MapPoint 생성자
    public MapPoint() {
        super();
    }

    // MapPoint 생성자 2
    public MapPoint(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // 이름 return
    public String getName() {
        return name;
    }

    // 이름 설정
    public void setName(String name) {
        this.name = name;
    }

    // 위도 return
    public double getLatitude() {
        return latitude;
    }

    // 위도 설정
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // 경도 return
    public double getLongitude() {
        return longitude;
    }

    // 경도 설정
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
