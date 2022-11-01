package com.example.ps21601.myapplication.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ps21601.myapplication.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DaNangMap extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_nang_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(16.075554, 108.169648);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("FPoly Đà Nẵng"));
        googleMap.addMarker(new MarkerOptions().position(sydney).title("FPT Polytechnic"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 20);
        googleMap.animateCamera(cameraUpdate, 3000, null);
    }
}