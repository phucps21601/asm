package com.example.ps21601.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ps21601.myapplication.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity {
    ImageButton btnCourse,btnMap,btnNews,btnSocial;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCourse = findViewById(R.id.btnCourse);
        btnLogout = findViewById(R.id.btnLogout);
        btnMap = findViewById(R.id.btnMap);
        btnNews = findViewById(R.id.btnNews);
        btnCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CourseActivity.class);
                startActivity(intent);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });
        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewsActivity.class);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("isLoggedIn");
                editor.remove("id");
                editor.remove("email");
                editor.remove("role");
                editor.apply();



                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();

                GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(MainActivity.this,gso);
                googleSignInClient.signOut();

                LoginManager.getInstance().logOut();

                Intent homeIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}