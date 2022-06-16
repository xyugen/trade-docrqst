package com.grouptwo.tradedocqst.request;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.users.StudentActivity;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    // setting elements
    Button btnRqstRequest, btnRqstCancel;
    ArrayList<String> documents;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        // connecting elements
        btnRqstRequest = findViewById(R.id.btnRqstRequest);
        btnRqstCancel = findViewById(R.id.btnRqstCancel);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = fAuth.getCurrentUser();

        // onclick
        btnRqstRequest.setOnClickListener(this);
        btnRqstCancel.setOnClickListener(this);

        // retrieve document data
        final Bundle docs = getIntent().getExtras();
        documents = docs.getStringArrayList("docs");
        Log.i("documentsHere", documents.toString());
        Toast.makeText(getApplicationContext(), documents.toString(), Toast.LENGTH_SHORT).show();
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