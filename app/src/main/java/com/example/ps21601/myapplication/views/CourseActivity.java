package com.example.ps21601.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ps21601.myapplication.R;
import com.example.ps21601.myapplication.adapter.CourseAdapter;
import com.example.ps21601.myapplication.models.AppCourse;
import com.example.ps21601.myapplication.services.CourseService;
import com.example.ps21601.myapplication.services.UserService;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    private ListView lvCourse;
    Button btnThemmon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        lvCourse = findViewById(R.id.lvCourses);
        btnThemmon = findViewById(R.id.btnHienbangthem);

        lvCourse.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AppCourse course = (AppCourse) parent.getItemAtPosition(position);
                new AlertDialog.Builder(CourseActivity.this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa không?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CourseActivity.this,CourseService.class);
                                intent.setAction(CourseService.COURSE_SERVICE_ACTION_DELETE);
                                intent.putExtra("id",course.getId());
                                startService(intent);
                            }
                        })
                                .setNegativeButton("Không",null).show();
                return true;
            }
        });
        lvCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppCourse course = (AppCourse) parent.getItemAtPosition(position);
                new AlertDialog.Builder(CourseActivity.this)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có muốn cập nhật khóa học ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Dồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(CourseActivity.this,UpdateCourseActivity.class);
                                intent.putExtra("id",course.getId());
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Không",null).show();
            }
        });
        btnThemmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this,InsertCourseActivity.class);
                startActivity(intent);
            }
        });

    }


    public void onGetCourses(){
        Intent intent = new Intent(this, CourseService.class);
        intent.setAction(CourseService.COURSE_SERVICE_ACTION_GET_ALL);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onGetCourses();
        IntentFilter courseFilter = new IntentFilter(CourseService.COURSE_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(getCourseReceiver, courseFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(getCourseReceiver);
    }


    private BroadcastReceiver getCourseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<AppCourse> result1 = (ArrayList<AppCourse>) intent.getSerializableExtra("result");
            CourseAdapter adapter = new CourseAdapter(result1);
            lvCourse.setAdapter(adapter);
        }
    };
}