package com.grouptwo.tradedocqst.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.tradedocqst.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    // setting up elements
    Button btnSave, btnBack;
    TextInputLayout tilSection;
    TextInputEditText edtFullName, edtLRN, edtPass;
    AutoCompleteTextView actSection;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // connecting text fields and buttons
        tilSection = findViewById(R.id.txtFldProfileSection);
        actSection = findViewById(R.id.actProfileSection);
        edtFullName = findViewById(R.id.edtTxtProfileFullName);
        edtLRN = findViewById(R.id.edtTxtProfileLRN);
        edtPass = findViewById(R.id.edtTxtProfilePassword);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        // apply onClick Listener
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSave){
            return;
        }
        else if (id == R.id.btnBack){
            onBackPressed();
        }
    }
}