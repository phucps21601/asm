package com.example.ps21601.myapplication.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.ps21601.myapplication.dao.UserDAO;
import com.example.ps21601.myapplication.models.AppUser;

public class UserService extends IntentService {
    public static final String USER_SERVICE_EVENT = "USER_SERVICE_EVENT";
    public static final String USER_SERVICE_ACTION_LOGIN = "USER_SERVICE_ACTION_LOGIN";
    public static final String USER_SERVICE_ACTION_REGISTER = "USER_SERVICE_ACTION_REGISTER";

    private UserDAO userDAO;

    public UserService(){
        super("UserService");
        userDAO = new UserDAO(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            String action = intent.getAction();
            if(action.equals(USER_SERVICE_ACTION_LOGIN)){
                String email = intent.getStringExtra("email");
                String password = intent.getStringExtra("password");
                AppUser appUser = userDAO.dangNhap(email, password);
                Intent outIntent = new Intent(USER_SERVICE_EVENT);
                outIntent.putExtra("result",appUser);
                LocalBroadcastManager.getInstance(UserService.this)
                        .sendBroadcast(outIntent);
            }
            else if(action.equals(USER_SERVICE_ACTION_REGISTER)){
                String email = intent.getStringExtra("email");
                String password = intent.getStringExtra("password");
                Integer role = intent.getIntExtra("role",1);
                Boolean result = userDAO.dangKy(email, password, role);
                Intent outIntent = new Intent(USER_SERVICE_EVENT);
                outIntent.putExtra("action",USER_SERVICE_ACTION_REGISTER);
                outIntent.putExtra("result",result);
                LocalBroadcastManager.getInstance(UserService.this)
                        .sendBroadcast(outIntent);
            }
        }
    }
}
