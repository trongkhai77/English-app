package com.example.midtermfull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText edtUserName, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentManager.class);
//                Intent intent = new Intent(MainActivity.this, StudentAndCertificateDetail.class);
                startActivity(intent);

            }
        });
//        edtUserName = (EditText) findViewById(R.id.edt_user_name);
//        edtPassword = (EditText) findViewById(R.id.editText);
//
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // open User Manager
//                Intent moveToUserManager = new Intent(MainActivity.this, UserManager.class);
//                startActivity(moveToUserManager);

//                // open Add User
////                Intent moveToAddUser = new Intent(MainActivity.this, Add_user.class);
////                startActivity(moveToAddUser);
//
//                // open Student detail
////                Intent moveToStudentDetail = new Intent(MainActivity.this, Student_detail.class);
////                startActivity(moveToStudentDetail);
//
//                //open Student and Certificate Detail
////                Intent moveToStudentAndCertificateDetail = new Intent(MainActivity.this, StudentAndCertificateDetail.class);
////                startActivity(moveToStudentAndCertificateDetail);
//
//                //open Add new Student
////                Intent moveToAddNewStudent = new Intent(MainActivity.this, AddNewStudent.class);
////                startActivity(moveToAddNewStudent);
//            }
//        });
//    }
    }
}