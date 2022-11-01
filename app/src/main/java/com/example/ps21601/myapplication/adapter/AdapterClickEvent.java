package com.example.ps21601.myapplication.adapter;

import com.example.ps21601.myapplication.models.AppCourse;

public interface AdapterClickEvent {
    public void onEditCourseClick(AppCourse appCourse);
    public void onDeleteCourseClick(AppCourse appCourse);
}
