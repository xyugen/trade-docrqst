package com.grouptwo.tradedocqst.request;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.login.SignUpActivity;
import com.grouptwo.tradedocqst.users.StudentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {

    // setting elements
    TextView txtLRN, txtFullName, txtSection, txtDocuments;
    Button btnRqstRequest, btnRqstCancel;
    ArrayList<String> documents;
    Object[] docsArray;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        // connecting elements
        txtLRN = findViewById(R.id.txtLRNCon);
        txtFullName = findViewById(R.id.txtFullNameCon);
        txtSection = findViewById(R.id.txtSectionCon);
        txtDocuments = findViewById(R.id.txtDocsRqstdCon);
        btnRqstRequest = findViewById(R.id.btnRqstRequest);
        btnRqstCancel = findViewById(R.id.btnRqstCancel);

        currentDate = java.text.DateFormat.getDateInstance().format(new Date());

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

        // show request data/receipt
        getRequestData(fUser.getUid());
        showDocumentsRequested();
    }

    private void showDocumentsRequested() {
        StringBuilder builder = new StringBuilder();
        for (String docs : documents){
            builder.append(docs).append("\n");
        }
        txtDocuments.setText(builder.toString());
    }

    private void getRequestData(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        // extract the data from the document
        df.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
            txtFullName.setText(documentSnapshot.getString("FullName"));
            txtSection.setText(documentSnapshot.getString("Section"));
            txtLRN.setText(documentSnapshot.getString("LRN"));
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnRqstRequest){
            requestDocuments();
        }
        else if (id == R.id.btnRqstCancel){
            startActivity(new Intent(getApplicationContext(), StudentActivity.class));
            finish();
        }
    }

    private void requestDocuments() {
        assert fUser != null;
        DocumentReference df = fStore.collection("Users").document(fUser.getUid()).collection("Requests").document();
        requestID(df.getId());

        Map<String, Object> docRequest = new HashMap<>();
        docRequest.put("FullName", Objects.requireNonNull(txtFullName.getText()).toString().trim());
        docRequest.put("Section", txtSection.getText().toString().trim());
        docRequest.put("LRN", Objects.requireNonNull(txtLRN.getText()).toString().trim());

        // put in documents array
        docsArray = documents.toArray();
        docRequest.put("Documents", Arrays.asList(docsArray));

        // date requested
        docRequest.put("RequestDate", currentDate);

        // queued
        docRequest.put("Done", 0);
        Log.d("Requested", "Documents" + documents);
        Log.d("RequestDate", "Date" + currentDate);

        df.set(docRequest)
                .addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "Document/s request success", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Document/s request failed", Toast.LENGTH_SHORT).show());

        startActivity(new Intent(getApplicationContext(), StudentActivity.class));
        finish();
    }

    private void requestID(String id) {
        DocumentReference df = fStore.collection("RequestIDs").document(fUser.getUid());

        Map<String, Object> docInfo = new HashMap<>();
        docInfo.put("RequestIDs", FieldValue.arrayUnion(id));

        df.set(docInfo, SetOptions.merge());
    }
}