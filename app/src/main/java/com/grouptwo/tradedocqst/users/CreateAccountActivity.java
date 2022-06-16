package com.grouptwo.tradedocqst.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.login.SignUpActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    // setting elements
    TextInputEditText edtFullName, edtEmail, edtPassword, edtConfPassword;
    AutoCompleteTextView actSection;
    Button btnBack, btnCreate;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // connecting elements
        edtFullName = findViewById(R.id.edtTxtCreateFullName);
        edtEmail = findViewById(R.id.edtTxtCreateEmail);
        edtPassword = findViewById(R.id.edtTxtCreatePassword);
        edtConfPassword = findViewById(R.id.edtTxtCreateConfPassword);
        actSection = findViewById(R.id.actCreateSection);
        btnCreate = findViewById(R.id.btnCACreateAccount);
        btnBack = findViewById(R.id.btnCABack);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = fAuth.getCurrentUser();

        // applying onclick listener
        btnBack.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        // populate drop down
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sections));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actSection.setAdapter(myAdapter);
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
        String val = Objects.requireNonNull(edtPassword.getText()).toString().trim();

        if (val.isEmpty()){
            edtPassword.setError("Password can't be empty");
            return false;
        } else if (val.length() < 8) {
            edtPassword.setError("Password must be minimum 8 characters");
            return false;
        } else {
            edtPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfPassword(){
        String val = Objects.requireNonNull(edtPassword.getText()).toString().trim();
        String valC = Objects.requireNonNull(edtConfPassword.getText()).toString().trim();

        if (valC.isEmpty() || !valC.equals(val)){
            edtConfPassword.setError("Confirm password does not match");
            return false;
        } else {
            edtConfPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCABack){
            onBackPressed();
        } else if (id == R.id.btnCACreateAccount){
            createUser();
        }
    }

    private void createUser() {
        if (validateFullName() && validateSection() && SignUpActivity.validateEmail(edtEmail) && validatePassword() && validateConfPassword()){
            fAuth.createUserWithEmailAndPassword(Objects.requireNonNull(edtEmail.getText()).toString().trim(), Objects.requireNonNull(edtPassword.getText()).toString().trim())
                    .addOnCompleteListener(authResult -> {
                        if (authResult.isSuccessful()) {
                            // send verif email
                            fUser.sendEmailVerification()
                                    .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Verification email has been sent.", Toast.LENGTH_LONG).show())
                                    .addOnFailureListener(e -> Log.d("TAG", "onFailure: Email not sent " + e.getMessage()));

                            Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
                            assert fUser != null;
                            DocumentReference df = fStore.collection("Users").document(fUser.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FullName", Objects.requireNonNull(edtFullName.getText()).toString().trim());
                            userInfo.put("Section", actSection.getText().toString().trim());
                            userInfo.put("UserEmail", edtEmail.getText().toString().trim());

                            // specify if the user is student/teacher/admin
                            userInfo.put("userGroup", "teacher");

                            df.set(userInfo);
                        }
                    }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to create account", Toast.LENGTH_SHORT).show());
        }
    }
}