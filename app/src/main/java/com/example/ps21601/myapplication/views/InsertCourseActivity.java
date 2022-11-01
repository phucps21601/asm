package com.example.ps21601.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ps21601.myapplication.R;
import com.example.ps21601.myapplication.services.CourseService;

public class InsertCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_addcourse);
        EditText txtMamon = findViewById(R.id.txtMaMon);
        EditText txtTenMon = findViewById(R.id.txtTenMon);
        EditText txtThoigian =findViewById(R.id.txtThoigianMon);
        EditText txtPhongmon = findViewById(R.id.txtPhongMon);
        Button btnThem = findViewById(R.id.btnThemmonhoc);
        Button btnHuy = findViewById(R.id.btnHuymonhoc);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = txtMamon.getText().toString();
                String ten = txtTenMon.getText().toString();
                String thoigian = txtThoigian.getText().toString();
                String phongmon = txtPhongmon.getText().toString();
                if(!ma.isEmpty() && !ten.isEmpty() && !thoigian.isEmpty() && !phongmon.isEmpty()){
                    Intent intent = new Intent(InsertCourseActivity.this, CourseService.class);
                    intent.putExtra("code",ma);
                    intent.putExtra("name",ten);
                    intent.putExtra("time",thoigian);
                    intent.putExtra("room",phongmon);
                    intent.setAction(CourseService.COURSE_SERVICE_ACTION_INSERT);
                    startService(intent);
                }
                else{
                    Toast.makeText(InsertCourseActivity.this,"Thêm không thành công",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertCourseActivity.this,CourseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter insertFilter = new IntentFilter(CourseService.COURSE_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(insertCourseReceiver,insertFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(insertCourseReceiver);
    }

    private BroadcastReceiver insertCourseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean result = intent.getBooleanExtra("result",false);
            if(result){
                Toast.makeText(InsertCourseActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                Log.d(">>>>>>TAG","hai");
                finish();
            }
            else{
                Toast.makeText(InsertCourseActivity.this,"Thêm không thành công",Toast.LENGTH_LONG).show();
                Log.d(">>>>>>TAG","hai");
            }
        }
    };
}