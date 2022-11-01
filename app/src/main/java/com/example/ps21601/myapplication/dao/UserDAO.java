package com.example.ps21601.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps21601.myapplication.database.Database;
import com.example.ps21601.myapplication.models.AppUser;

public class UserDAO {
    private Database database;
    public UserDAO(Context context){
        database = Database.getInstance(context);
    }

    public boolean dangKy(String email,String password,Integer role){
        Boolean ketQua = false;
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction(); //bat dau tuong tac voi db
        try {
            ContentValues values = new ContentValues();
            values.put("EMAIL",email); // email dung dinh dang , ko trung
            values.put("PASSWORD",password); // ma hoa(hash password)
            values.put("ROLE",role); //
            long rows = db.insertOrThrow("USERS",null,values);
            ketQua = rows>=0;
            db.setTransactionSuccessful(); //hoan thanh , luu vao db
        } catch (Exception e){
            Log.d(">>>>>TAG","dangKy: "+e.getMessage());
        } finally {
            db.endTransaction(); //ket thuc , dong ket noi
        }
        return ketQua;
    }

    public AppUser dangNhap(String email, String password){
        AppUser appUser = null;
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT ID, EMAIL, PASSWORD, ROLE FROM USERS WHERE EMAIL = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{email});
        try {
            if(cursor.moveToFirst()){
                while (!cursor.isAfterLast()){
                    Integer _id = cursor.getInt(0);
                    String _email = cursor.getString(1);
                    String _password = cursor.getString(2);
                    Integer _role = cursor.getInt(3);
                    if(!password.equals(_password)){
                        break;
                    }
                    else{
                        appUser = new AppUser(_id,_role,_email,null);
                    }
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
        return appUser;
    }


}
