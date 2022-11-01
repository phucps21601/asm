package com.example.ps21601.myapplication.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.ps21601.myapplication.dao.CourseDAO;
import com.example.ps21601.myapplication.models.AppCourse;

import java.util.ArrayList;

public class CourseService extends IntentService {
    public static final String COURSE_SERVICE_EVENT= "COURSE_SERVICE_EVENT";
    public static final String COURSE_SERVICE_ACTION_INSERT= "COURSE_SERVICE_ACTION_INSERT";
    public static final String COURSE_SERVICE_ACTION_UPDATE= "COURSE_SERVICE_ACTION_UPDATE";
    public static final String COURSE_SERVICE_ACTION_DELETE= "COURSE_SERVICE_ACTION_DELETE";
    public static final String COURSE_SERVICE_ACTION_GET_ALL= "COURSE_SERVICE_ACTION_GET_ALL";

    private CourseDAO courseDAO;
    public CourseService(){
        super("CourseService");
        courseDAO = new CourseDAO(this);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            String action = intent.getAction();
            switch (action){
                case COURSE_SERVICE_ACTION_INSERT: {
                    String code = intent.getStringExtra("code");
                    String name = intent.getStringExtra("name");
                    String time = intent.getStringExtra("time");
                    String room = intent.getStringExtra("room");
                    AppCourse course = new AppCourse(null, code, name, time, room);
                    Boolean result = courseDAO.insert(course);
                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                    outIntent.putExtra("result",result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case COURSE_SERVICE_ACTION_UPDATE: {
                        Integer id = intent.getIntExtra("id",0);
                        String code = intent.getStringExtra("code");
                        String name = intent.getStringExtra("name");
                        String time = intent.getStringExtra("time");
                        String room = intent.getStringExtra("room");
                        AppCourse course = new AppCourse(id, code, name, time, room);
                        Boolean result = courseDAO.update(course);
                        Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                        outIntent.putExtra("result",result);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                case COURSE_SERVICE_ACTION_DELETE: {
                    Integer id = intent.getIntExtra("id",0);
                    if(id > 0){
                        Boolean result = courseDAO.delete(id);
                        if(result){
                            ArrayList<AppCourse> courses = courseDAO.getAll();
                            Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                            outIntent.putExtra("result",courses);
                            LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                        }
                    }
                    break;
                }
                case COURSE_SERVICE_ACTION_GET_ALL: {
                    ArrayList<AppCourse> result = courseDAO.getAll();
                    Intent outIntent = new Intent(COURSE_SERVICE_EVENT);
                    outIntent.putExtra("result",result);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(outIntent);
                    break;
                }
                default:
                    break;
            }
        }
    }
}
