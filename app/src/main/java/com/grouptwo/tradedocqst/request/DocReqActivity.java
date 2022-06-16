package com.grouptwo.tradedocqst.request;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.users.StudentActivity;

import java.util.ArrayList;

public class DocReqActivity extends AppCompatActivity implements View.OnClickListener {

    // setting elements
    Button btnDocRqstBack, btnDocRqstNext;
    CheckBox form137, form138, upPer, goodMoral, cOE;
    ArrayList<String> documents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docreq);

        // connecting elements
        btnDocRqstBack = findViewById(R.id.btnDocRqstBack);
        btnDocRqstNext = findViewById(R.id.btnDocRqstNext);
        // checkboxes
        form137 = findViewById(R.id.chkForm173);
        form138 = findViewById(R.id.chkForm138);
        upPer = findViewById(R.id.chkUpPer);
        goodMoral = findViewById(R.id.chkGoodMoral);
        cOE = findViewById(R.id.chkCOE);
        // array
        documents = new ArrayList<>();

        // applying onClick listener
        btnDocRqstBack.setOnClickListener(this);
        btnDocRqstNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnDocRqstBack) {
            startActivity(new Intent(getApplicationContext(), StudentActivity.class));
            finish();
        }
        else if (id == R.id.btnDocRqstNext){
            takeCheckBoxData();
        }
    }

    private void takeCheckBoxData() {
        boolean checked = false;
        if (form137.isChecked()){
            documents.add(form137.getText().toString());
            checked = true;
        }
        if (form138.isChecked()){
            documents.add(form138.getText().toString());
            checked = true;
        }
        if (upPer.isChecked()){
            documents.add(upPer.getText().toString());
            checked = true;
        }
        if (goodMoral.isChecked()){
            documents.add(goodMoral.getText().toString());
            checked = true;
        }
        if (cOE.isChecked()){
            documents.add(cOE.getText().toString());
            checked = true;
        }
        if (!checked) {
            Toast.makeText(getApplicationContext(), "Check at least one", Toast.LENGTH_SHORT).show();
        } else {
            // pass documents data to another page
            Log.i("myStringArray", documents.toString());
            Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
            intent.putStringArrayListExtra("docs", documents);
            startActivity(intent);
            finish();
        }
    }
}