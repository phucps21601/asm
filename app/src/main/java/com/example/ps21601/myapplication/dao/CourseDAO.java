package com.example.ps21601.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps21601.myapplication.database.Database;
import com.example.ps21601.myapplication.models.AppCourse;
import com.example.ps21601.myapplication.models.AppUser;

import java.util.ArrayList;

public class CourseDAO {
    private Database database;
    public CourseDAO(Context context){
        database = Database.getInstance(context);
    }
    public ArrayList<AppCourse> getAll(){
        ArrayList<AppCourse> list = new ArrayList<>();
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, CODE, NAME, TIME, ROOM FROM COURSES";
        Cursor cursor = db.rawQuery(sql,null);
        try {
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer _id = cursor.getInt(0);
                    String _code = cursor.getString(1);
                    String _name = cursor.getString(2);
                    String _time = cursor.getString(3);
                    String _room = cursor.getString(4);

                    AppCourse course = new AppCourse(_id, _code, _name, _time, _room);
                    list.add(course);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e){
            Log.d(">>>>>>TAG","dangNhap: " + e.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return list;
    }
    public Boolean insert(AppCourse course){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); //bat dau tuong tac voi db
        try {
            ContentValues values = new ContentValues();
            values.put("CODE",course.getCode()); // email dung dinh dang , ko trung
            values.put("NAME",course.getName()); // ma hoa(hash password)
            values.put("TIME",course.getTime()); //
            values.put("ROOM",course.getRoom());
            long rows = db.insertOrThrow("COURSES",null,values);
            result = rows>=0;
            db.setTransactionSuccessful(); //hoan thanh , luu vao db
        } catch (Exception e){
            Log.d(">>>>>TAG","dangKy: "+e.getMessage());
        } finally {
            db.endTransaction(); //ket thuc , dong ket noi
        }
        return result;
    }

    public Boolean delete(Integer id){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); //bat dau tuong tac voi db
        try {
            long rows = db.delete("COURSES"," ID = ? ",
                    new String[]{id.toString()});
            result = rows>=0;
            db.setTransactionSuccessful(); //hoan thanh , luu vao db
        } catch (Exception e){
            Log.d(">>>>>TAG","dangKy: "+e.getMessage());
        } finally {
            db.endTransaction(); //ket thuc , dong ket noi
        }
        return result;
    }

    public Boolean update(AppCourse course){
        Boolean result = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); //bat dau tuong tac voi db
        try {
            ContentValues values = new ContentValues();
            values.put("CODE",course.getCode()); // email dung dinh dang , ko trung
            values.put("NAME",course.getName()); // ma hoa(hash password)
            values.put("TIME",course.getTime()); //
            values.put("ROOM",course.getRoom());
            long rows = db.update("COURSES",values," ID = ? ",
                    new String[]{course.getId().toString()});
            result = rows>=0;
            db.setTransactionSuccessful(); //hoan thanh , luu vao db
        } catch (Exception e){
            Log.d(">>>>>TAG","dangKy: "+e.getMessage());
        } finally {
            db.endTransaction(); //ket thuc , dong ket noi
        }
        return result;
    }
}
