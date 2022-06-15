package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grouptwo.tradedocqst.users.StudentActivity;

public class DocReqActivity extends AppCompatActivity implements View.OnClickListener {

    // setting buttons
    Button btnDocRqstBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docreq);

        // connection buttons
        btnDocRqstBack = findViewById(R.id.btnDocRqstBack);

        // applying onClick listener
        btnDocRqstBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnDocRqstBack) {
            startActivity(new Intent(this, StudentActivity.class));
            finish();
        }
    }
}