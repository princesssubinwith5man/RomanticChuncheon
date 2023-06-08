package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;


import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private InfoWindow infoWindow;
    String centername;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT); }
        mapView = findViewById(R.id.map_fragment);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        Intent intent = getIntent();
        centername = intent.getExtras().getString("centername");
        address = intent.getExtras().getString("add");

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        //String address = "강원도 춘천시 춘천로204번길 26(효자동)"; //쿼드 주소
        Location location = addToPoint(this,address);
        naverMap.setMapType(NaverMap.MapType.Basic); // 맵 타입 설정
        /*
        Basic: 일반 지도. 하천, 녹지, 도로, 심벌 등 다양한 정보를 노출
        Navi: 차량용 내비게이션에 특화된 지도
        Satellite: 위성 지도. 심벌, 도로 등 위성 사진을 제외한 요소는 노출되지 않음
        Hybrid: 위성 사진과 도로, 심벌을 함께 노출하는 하이브리드 지도
        Terrain: 지형도. 산악 지형을 실제 지형과 유사하게 입체적으로 표현
         */
        //naverMap.setNightModeEnabled(true); // 야간 모드 (MapType.Navi에서만 적용됨)
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(location.getLatitude(), location.getLongitude()))
                .animate(CameraAnimation.Fly);
        naverMap.moveCamera(cameraUpdate); //카메라 이동

        Marker marker = new Marker(); //마커 생성
        marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude())); //마커 위치
        marker.setMap(naverMap);
        marker.setOnClickListener(this::onClick); //마커 클릭리스너

        infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) { //정보창 내용
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return centername;
            }
        });
    }

    public boolean onClick(@NonNull Overlay overlay){  //마커 클릭시 정보창 띄우기
        if(overlay instanceof Marker){
            Marker marker = (Marker) overlay;
            if(marker.getInfoWindow() != null){
                infoWindow.close();
            }
            else{
                infoWindow.open(marker);
            }
            return true;
        }
        return false;
    }
    public static Location addToPoint(Context context, String address){ //주소를 위도,경도로 변환
        Location location = new Location("");
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;

        try{
            addresses = geocoder.getFromLocationName(address,3);
        } catch(IOException e){
            e.printStackTrace();
        }
        if(addresses != null){
            for(int i=0;i<addresses.size();i++){
                Address lating = addresses.get(i);
                location.setLatitude(lating.getLatitude());
                location.setLongitude(lating.getLongitude());
            }
        }
        return location;
    }

}