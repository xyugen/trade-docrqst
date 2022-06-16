package com.grouptwo.tradedocqst.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.tradedocqst.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    // setting up elements
    Button btnSave, btnBack;
    TextInputLayout tilSection;
    TextInputEditText edtFullName, edtLRN, edtPass;
    AutoCompleteTextView actSection;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        uID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

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

        // populate drop down
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sections));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actSection.setAdapter(myAdapter);

        // get data then pass onto textfields
        getProfileData(uID);
    }

    private void getProfileData(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
            edtFullName.setText(documentSnapshot.getString("FullName"));
            actSection.setText(documentSnapshot.getString("Section"));
            edtLRN.setText(documentSnapshot.getString("LRN"));
        });
    }

    private void updateProfileData(String uid){
        DocumentReference df = fStore.collection("Users").document(uid);
        Map<String, Object> map = new HashMap<>();
        map.put("FullName", Objects.requireNonNull(edtFullName.getText()).toString().trim());
        map.put("Section", actSection.getText().toString().trim());
        map.put("LRN", Objects.requireNonNull(edtLRN.getText()).toString().trim());
        FirebaseUser fUser = fAuth.getCurrentUser();

        // update password
        assert fUser != null;
        fUser.updatePassword(Objects.requireNonNull(edtPass.getText()).toString().trim());

        // update data on firestore
        df.update(map)
                .addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "Save success", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Save failed", Toast.LENGTH_SHORT).show());
    }

    private boolean validateLRN(){
        String val = Objects.requireNonNull(edtLRN.getText()).toString().trim();

        if (val.isEmpty()){
            edtLRN.setError("This field is required");
            return false;
        } else if (edtLRN.length() != 12){
            edtLRN.setError("LRN must be 12 numbers long");
            return false;
        } else {
            edtLRN.setError(null);
            return true;
        }
    }

    private boolean validateFullName(){
        String val = Objects.requireNonNull(edtFullName.getText()).toString().trim();

        if (val.isEmpty()){
            edtFullName.setError("This field is required");
            return false;
        } else {
            edtFullName.setError(null);
            return true;
        }
    }

    private boolean validateSection(){
        String val = actSection.getText().toString().trim();
        String[] sections = getResources().getStringArray(R.array.sections);

        if (val.isEmpty()){
            actSection.setError("This field is required");
            return false;
        } else if (!Arrays.asList(sections).contains(actSection.getText().toString().trim())){
            actSection.setError("Select a section");
            return false;
        }
        else {
            actSection.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = Objects.requireNonNull(edtPass.getText()).toString().trim();

        if (val.isEmpty()){
            edtPass.setError("Password can't be empty");
            return false;
        } else if (val.length() < 8) {
            edtPass.setError("Password must be minimum 8 characters");
            return false;
        } else {
            edtPass.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSave){
            if(validateFullName() && validateLRN() && validateSection() && validatePassword()){
                updateProfileData(uID);
            }
        }
        else if (id == R.id.btnBack){
            onBackPressed();
        }
    }
}