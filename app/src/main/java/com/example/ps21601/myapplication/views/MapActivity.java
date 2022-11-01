package com.example.ps21601.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ps21601.myapplication.R;
import com.example.ps21601.myapplication.maps.CanThoMap;
import com.example.ps21601.myapplication.maps.DaNangMap;
import com.example.ps21601.myapplication.maps.HaNoiMap;
import com.example.ps21601.myapplication.maps.HoChiMinhMap;
import com.example.ps21601.myapplication.maps.TayNguyenMap;

import java.util.List;

public class MapActivity extends AppCompatActivity {

    Button btnHCM,btnHanoi,btnDanang,btnTaynguyen,btnCanTho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        btnHCM = findViewById(R.id.btnHCM);
        btnHanoi = findViewById(R.id.btnHaNoi);
        btnCanTho = findViewById(R.id.btnCanTho);
        btnDanang = findViewById(R.id.btnDaNang);
        btnTaynguyen = findViewById(R.id.btnTayNguyen);

        btnHCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, HoChiMinhMap.class);
                startActivity(intent);
            }
        });
        btnHanoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, HaNoiMap.class);
                startActivity(intent);
            }
        });
        btnCanTho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, CanThoMap.class);
                startActivity(intent);
            }
        });
        btnTaynguyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, TayNguyenMap.class);
                startActivity(intent);
            }
        });
        btnDanang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, DaNangMap.class);
                startActivity(intent);
            }
        });
    }


}