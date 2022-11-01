package com.example.ps21601.myapplication.models;

import java.io.Serializable;

public class AppCourse implements Serializable {
    private Integer id;
    private String code, name, time, room,courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public AppCourse() {
    }

    public AppCourse(Integer id, String code, String name, String time, String room) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.time = time;
        this.room = room;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
