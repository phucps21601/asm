package com.example.ps21601.myapplication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ps21601.myapplication.R;
import com.example.ps21601.myapplication.services.UserService;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail,edtPassword;
    private Button btnRegister;
    private Spinner spnRole;
    private Integer role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        spnRole = findViewById(R.id.spnView);
        ArrayList<String> arrayListRole = new ArrayList<String>();
        arrayListRole.add("Admin");
        arrayListRole.add("Sinh viên");
        ArrayAdapter arrayAdapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_spinner_item,arrayListRole);
        spnRole.setAdapter(arrayAdapter);
        spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(arrayListRole.get(position).equals("Admin")){
                    role = 0;
                }
                else{
                    role = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                role = 1;
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!password.isEmpty() && !email.isEmpty()){
                        if(email.matches(emailPattern)){
                            Intent intent = new Intent(RegisterActivity.this, UserService.class);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            intent.putExtra("role",role);
                            intent.setAction(UserService.USER_SERVICE_ACTION_REGISTER);
                            startService(intent);
                        }else{
                            Toast.makeText(RegisterActivity.this,"EMAIL không đúng định dạng",Toast.LENGTH_LONG).show();
                        }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Không bỏ trống EMAIL và PASSWORD",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter registerFilter = new IntentFilter(UserService.USER_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(register, registerFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(register);
    }


    private BroadcastReceiver register = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean result = intent.getBooleanExtra("result",false);
            if(result){
                Toast.makeText(RegisterActivity.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                Log.d(">>>>>>TAG","hai");
                finish();
            }
            else{
                Toast.makeText(RegisterActivity.this,"Đăng ký không thành công",Toast.LENGTH_LONG).show();
                Log.d(">>>>>>TAG","hai");
            }
        }
    };
}