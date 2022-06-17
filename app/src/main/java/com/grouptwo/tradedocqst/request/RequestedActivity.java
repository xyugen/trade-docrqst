package com.grouptwo.tradedocqst.request;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.users.StudentActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestedActivity extends AppCompatActivity implements View.OnClickListener {

    // setting elements
    TextView txtLRN, txtFullName, txtSection, txtDocuments, txtRqstDate;
    Button btnDone, btnBack;
    ArrayList<String> documents;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;
    String currentDate;
    String rID;
    ArrayList<String> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested);

        // connecting elements
        txtLRN = findViewById(R.id.txtLRNCon);
        txtFullName = findViewById(R.id.txtFullNameCon);
        txtSection = findViewById(R.id.txtSectionCon);
        txtDocuments = findViewById(R.id.txtDocsRqstdCon);
        txtRqstDate = findViewById(R.id.txtRqstDateCon);
        btnDone = findViewById(R.id.btnRqstdDone);
        btnBack = findViewById(R.id.btnRqstdBack);

        currentDate = java.text.DateFormat.getDateInstance().format(new Date());

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = fAuth.getCurrentUser();

        // onclick
        btnDone.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        // get intent request id
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            rID = extras.getString("rID");
        }

        // get data
        getRequestData(rID);
        showDocumentsRequested();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnRqstdDone) {
            setRequestDone();
        } else if (id == R.id.btnRqstdBack) {
            onBackPressed();
        }
    }

    private void showDocumentsRequested() {
        Task<DocumentSnapshot> df = fStore.collection("Users")
                .document(fUser.getUid()).collection("Requests").document(rID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot ds = task.getResult();
                            if (ds.exists()){
                                documents = (ArrayList<String>) ds.get("Documents");

                                StringBuilder builder = new StringBuilder();
                                for (String docs : documents){
                                    builder.append(docs).append("\n");
                                }
                                txtDocuments.setText(builder.toString());
                            }
                        }
                    }
                });
    }

    private void getRequestData(String rID) {
        DocumentReference df = fStore.collection("Users").document(fUser.getUid()).collection("Requests").document(rID);
        df.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
            txtFullName.setText(documentSnapshot.getString("FullName"));
            txtSection.setText(documentSnapshot.getString("Section"));
            txtLRN.setText(documentSnapshot.getString("LRN"));
            txtRqstDate.setText(documentSnapshot.getString("RequestDate"));
        });
    }

    private void setRequestDone() {
        DocumentReference df = fStore.collection("Users").document(fUser.getUid()).collection("Requests").document(rID);
        Map<String, Object> map = new HashMap<>();
        map.put("Done", true);

        // update data on firestore
        df.update(map);

        startActivity(new Intent(getApplicationContext(), StudentActivity.class));
        finish();
    }
}