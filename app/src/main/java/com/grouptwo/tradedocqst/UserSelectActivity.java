package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSelectActivity extends AppCompatActivity implements View.OnClickListener{

    // creating the user buttons
    Button btnStudent, btnTeacher, btnAdmin;

    public void btnUser(String user){
        Intent myIntent = new Intent(UserSelectActivity.this, LoginActivity.class);

        myIntent.putExtra("user_group", user); // Parameters
        UserSelectActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uselect);

        // connecting buttons
        btnStudent = findViewById(R.id.btnStudent);
        btnTeacher = findViewById(R.id.btnTeacher);
        btnAdmin = findViewById(R.id.btnAdmin);

        // apply onClickListener over buttons
        btnStudent.setOnClickListener(this);
        btnTeacher.setOnClickListener(this);
        btnAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.btnStudent) {
            btnUser("Student");
        }
        else if(id == R.id.btnTeacher) {
            btnUser("Teacher");
        }
        else if(id == R.id.btnAdmin) {
            btnUser("Admin");
        }
    }
}
