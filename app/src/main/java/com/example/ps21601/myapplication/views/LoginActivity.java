package com.example.ps21601.myapplication.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ps21601.myapplication.R;
import com.example.ps21601.myapplication.models.AppUser;
import com.example.ps21601.myapplication.services.UserService;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private EditText edtLoginEmail, edtLoginPassowrd;
    private Button btnLogin, btnGoRegister;

    GoogleSignInClient gsc;

    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        readLogin(); // kiem tra trang thai login
        edtLoginEmail = findViewById(R.id.loginEmail);
        edtLoginPassowrd = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnloginDangNhap);
        btnGoRegister = findViewById(R.id.btnLoginDangky);

        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtLoginEmail.getText().toString();
                String password = edtLoginPassowrd.getText().toString();
                Intent intent = new Intent(LoginActivity.this, UserService.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.setAction(UserService.USER_SERVICE_ACTION_LOGIN);
                startService(intent);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(LoginActivity.this,gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
        SignInButton sib = findViewById(R.id.btnGoogle);
        sib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googleIntent = gsc.getSignInIntent();
                googleLauncher.launch(googleIntent);
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Log.d(">>>>>>TAG","onCancel: ");
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d(">>>>>>TAG","onError: " + e.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter loginFilter = new IntentFilter(UserService.USER_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver, loginFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(loginReceiver);
    }

    private BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AppUser result = (AppUser) intent.getSerializableExtra("result");
            if(result != null){
                writeLogin(result);
                Intent homeIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
            else{
                Toast.makeText(LoginActivity.this,"Đăng nhập không thành công",Toast.LENGTH_LONG).show();
            }
        }
    };

        ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            String email = account.getEmail();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e){
                            Log.d(">>>>>TAG","onActivityResultError: " + e.getMessage());
                        }
                    }
                }
        );
    private void writeLogin(AppUser appUser){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn",true);
        editor.putInt("id",appUser.getId());
        editor.putString("email",appUser.getEmail());
        editor.putInt("role",appUser.getRole());
        editor.commit();
    }


    private void readLogin(){
        SharedPreferences preferences = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE);
        Boolean isLoggin = preferences.getBoolean("isLoggedIn",false);
        if(isLoggin == true){
            Intent homeIntent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}