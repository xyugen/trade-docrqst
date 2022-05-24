package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSelectActivity extends AppCompatActivity implements View.OnClickListener{

    // creating the user buttons
    Button btnStudent, btnTeacher, btnAdmin;

    /* public void btnUser(String user){
        Intent myIntent = new Intent(UserSelectActivity.this, LoginActivity.class);

        myIntent.putExtra("user_group", user); // Parameters
        UserSelectActivity.this.startActivity(myIntent);
    } */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {
        //check if user is logged in
        //If user is logged in --> move to loginActivity

        SessionManagement sessionManagement = new SessionManagement(UserSelectActivity.this);
        int userGroupID = sessionManagement.getSession();

        if(userGroupID != -1){
            //user group id logged in and so move to loginActivity
            moveToLogin();
        }
    }

    public  void onClick(View v) {
        int id = v.getId();
        UserGroup userGroup = null;
        if(id == R.id.btnStudent) {
            userGroup = new UserGroup(0, "Student");
        }
        else if(id == R.id.btnTeacher) {
            userGroup = new UserGroup(1, "Teacher");
        }
        else if(id == R.id.btnAdmin) {
            userGroup = new UserGroup(2, "Admin");
        }

        SessionManagement sessionManagement = new SessionManagement(UserSelectActivity.this);
        sessionManagement.saveSession(userGroup);

        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(UserSelectActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /* @Override
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
    } */
}
