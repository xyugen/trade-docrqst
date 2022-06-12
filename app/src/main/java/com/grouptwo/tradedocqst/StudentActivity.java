package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    // set elements
    Button btnRqstDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // connect buttons
        btnRqstDoc = findViewById(R.id.btnRqstDoc);

        // apply onClick listener
        btnRqstDoc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnRqstDoc) {
            startActivity(new Intent(this, DocReqActivity.class));
            finish();
        }
    }
}