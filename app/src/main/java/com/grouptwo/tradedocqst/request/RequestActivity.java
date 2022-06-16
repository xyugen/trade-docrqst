package com.grouptwo.tradedocqst.request;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.users.StudentActivity;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    // setting elements
    Button btnRqstRequest, btnRqstCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        // connecting elements
        btnRqstRequest = findViewById(R.id.btnRqstRequest);
        btnRqstCancel = findViewById(R.id.btnRqstCancel);

        // onclick
        btnRqstRequest.setOnClickListener(this);
        btnRqstCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnRqstRequest){
            return;
        }
        else if (id == R.id.btnRqstCancel){
            startActivity(new Intent(getApplicationContext(), StudentActivity.class));
            finish();
        }
    }
}