package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    // set elements
    Button btnRqstDoc;
    ImageView imgAccDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // connect buttons
        btnRqstDoc = findViewById(R.id.btnRqstDoc);
        imgAccDrawer = findViewById(R.id.imgAccDrawer);

        // apply onClick listener
        btnRqstDoc.setOnClickListener(this);
        imgAccDrawer.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnRqstDoc) {
            startActivity(new Intent(this, DocReqActivity.class));
            finish();
        }
        if (id == R.id.imgAccDrawer) {

        }
    }
}