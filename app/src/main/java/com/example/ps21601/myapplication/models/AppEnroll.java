package com.example.ps21601.myapplication.models;

public class AppEnroll {
    private Integer id,courseId,userId;
    private Float joined;

    public AppEnroll(Integer id, Integer courseId, Integer userId, Float joined) {
        this.id = id;
        this.courseId = courseId;
        this.userId = userId;
        this.joined = joined;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getJoined() {
        return joined;
    }

    public void setJoined(Float joined) {
        this.joined = joined;
    }
}
