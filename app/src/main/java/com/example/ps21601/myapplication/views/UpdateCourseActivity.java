package com.example.ps21601.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import com.example.ps21601.myapplication.models.AppCourse;
import com.example.ps21601.myapplication.services.CourseService;

import java.util.ArrayList;

public class UpdateCourseActivity extends AppCompatActivity {
    EditText txtUpdateMa,txtUpdateTen,txtUpdateTime,txtUpdateRoom;
    Button btnUpdate,btnCancelUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ps21601.myapplication.R.layout.activity_update_course);
        txtUpdateMa = findViewById(R.id.txtUpdateMaMon);
        txtUpdateTen = findViewById(R.id.txtUpdateTenMon);
        txtUpdateRoom = findViewById(R.id.txtUpdatePhongMon);
        txtUpdateTime = findViewById(R.id.txtUpdateThoigianMon);
        btnUpdate = findViewById(R.id.btnCapnhat);
        btnCancelUpdate = findViewById(R.id.btnUpdateHuymonhoc);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = getIntent().getIntExtra("id",0);
                String ma = txtUpdateMa.getText().toString();
                String ten = txtUpdateTen.getText().toString();
                String thoigian = txtUpdateTime.getText().toString();
                String phongmon = txtUpdateRoom.getText().toString();
                if(!ma.isEmpty() && !ten.isEmpty() && !thoigian.isEmpty() && !phongmon.isEmpty()) {
                    Intent intent = new Intent(UpdateCourseActivity.this, CourseService.class);
                    intent.putExtra("id", id);
                    intent.putExtra("code", ma);
                    intent.putExtra("name", ten);
                    intent.putExtra("time", thoigian);
                    intent.putExtra("room", phongmon);
                    intent.setAction(CourseService.COURSE_SERVICE_ACTION_UPDATE);
                    startService(intent);
                }
                else{
                    Toast.makeText(UpdateCourseActivity.this,"Cập nhật không thành công",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateCourseActivity.this,CourseActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter updateFilter = new IntentFilter(CourseService.COURSE_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(updateReceiver,updateFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReceiver);
    }

    private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean result = intent.getBooleanExtra("result",false);
            if(result){
                Toast.makeText(UpdateCourseActivity.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                Log.d(">>>>>>TAG","hai");
                finish();
            }
            else{
                Toast.makeText(UpdateCourseActivity.this,"Cập nhật không thành công",Toast.LENGTH_LONG).show();
                Log.d(">>>>>>TAG","hai");
            }
        }
    };
}