package com.grouptwo.tradedocqst.request;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
    TextView txtLRN, txtFullName, txtSection, txtDocuments;
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
        txtLRN = findViewById(R.id.txtLRNCon);
        txtFullName = findViewById(R.id.txtFullNameCon);
        txtSection = findViewById(R.id.txtSectionCon);
        txtDocuments = findViewById(R.id.txtDocsRqstdCon);
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
            return;
        }
        else if (id == R.id.btnRqstCancel){
            startActivity(new Intent(getApplicationContext(), StudentActivity.class));
            finish();
        }
    }
}