package com.example.ps21601.myapplication.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ps21601.myapplication.R;
import com.example.ps21601.myapplication.adapter.AdapterClickEvent;
import com.example.ps21601.myapplication.adapter.CourseAdapter;
import com.example.ps21601.myapplication.models.AppCourse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseActivity extends AppCompatActivity implements AdapterClickEvent {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText edtName,edtCode,edtTime,edtRoom;
    private ListView lvCourse;

    private AppCourse course = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
         edtName = findViewById(R.id.edtTenmon);
          edtCode = findViewById(R.id.edtMamon);
         edtTime = findViewById(R.id.edtGioHoc);
         edtRoom = findViewById(R.id.edtPhongHoc);
         lvCourse = findViewById(R.id.lvCourse);

    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }

    public void onSaveClick(View view){
        String name = edtName.getText().toString();
        String code = edtCode.getText().toString();
        String time = edtTime.getText().toString();
        String room = edtRoom.getText().toString();

        // Create a new user with a first and last name
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("code", code);
        item.put("time", time);
        item.put("room", room);

        if(course == null){
            db.collection("course")
                    .add(item)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
                                    Toast.LENGTH_LONG).show();
                            readData();
                            course = null;
                            edtCode.setText(null);
                            edtName.setText(null);
                            edtRoom.setText(null);
                            edtTime.setText(null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FirebaseActivity.this,"Cap nhat khong thanh cong",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

        }
        else{
            db.collection("course")
                    .document(course.getCourseId())
                    .set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
                                    Toast.LENGTH_LONG).show();
                            readData();
                            course = null;
                            edtCode.setText(null);
                            edtName.setText(null);
                            edtRoom.setText(null);
                            edtTime.setText(null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FirebaseActivity.this,"Cap nhat thanh cong",
                                    Toast.LENGTH_LONG).show();
                            course = null;
                        }
                    });
        }

        // Add a new document with a generated ID
        db.collection("course")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(FirebaseActivity.this,"Inserted",
                                Toast.LENGTH_LONG).show();
                        readData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseActivity.this,"" + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void readData(){
        db.collection("course")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<AppCourse> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> map = document.getData();
                                String name = map.get("name").toString();
                                String code = map.get("code").toString();
                                String time = map.get("time").toString();
                                String room = map.get("room").toString();
                                AppCourse course = new AppCourse(-1,code,name,time,room);
                                course.setCourseId(document.getId());
                                list.add(course);
                            }
                            CourseAdapter adapter = new CourseAdapter(list);
                            lvCourse.setAdapter(adapter);
                        }

                    }
                });
    }

    @Override
    public void onEditCourseClick(AppCourse appCourse) {
        edtName.setText(appCourse.getName());
        edtRoom.setText(appCourse.getRoom());
        edtTime.setText(appCourse.getTime());
        edtCode.setText(appCourse.getCode());
    }

    @Override
    public void onDeleteCourseClick(AppCourse appCourse) {
        new AlertDialog.Builder(FirebaseActivity.this)
                .setTitle("Xoa")
                .setMessage("Xoa se mat vinh vien!!")
                .setNegativeButton("Huy",null)
                .setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("course")
                                .document(appCourse.getCourseId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(FirebaseActivity.this,"Xoa thanh cong",
                                                Toast.LENGTH_LONG).show();
                                        readData();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(FirebaseActivity.this,"Xoa khong thanh cong",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                }).show();
    }
}