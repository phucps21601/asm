package com.example.ps21601.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if(instance == null){
            instance = new Database(context);
        }
        return instance;
    }

    private Database(Context context){
        super(context,"MY_DATABASE",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUser ="CREATE TABLE IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " EMAIL TEXT, PASSWORD TEXT, ROLE INTEGER)";
        db.execSQL(sqlUser);

        String sqlCourses ="CREATE TABLE IF NOT EXISTS COURSES(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " CODE TEXT, NAME TEXT, TIME TEXT, ROOM TEXT)";
        db.execSQL(sqlCourses);

        String sqlEnrollS ="CREATE TABLE IF NOT EXISTS ENROLL(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "COURSES_ID INTEFER, USERS_ID INTEGER, DATE_JOINED FLOAT, FOREIGN KEY(USERS_ID) REFERENCES USERS(ID), FOREIGN KEY(COURSES_ID) REFERENCES COURSES(ID))";
        db.execSQL(sqlEnrollS);

        String k = "insert into COURSES (CODE, NAME, TIME, ROOM) values ('PYG','Silver-backed jackal','7:17 PM','79');";
        db.execSQL("insert into COURSES (CODE, NAME, TIME, ROOM) values ('PYG','Silver-backed jackal','7:17 PM','79')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
