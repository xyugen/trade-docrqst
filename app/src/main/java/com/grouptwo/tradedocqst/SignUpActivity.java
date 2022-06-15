package com.grouptwo.tradedocqst;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // Creating elements
    Button btnSUNext, btnBack, btnForgotPW;
    TextInputEditText edtLRN, edtEmail, edtPass;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // connecting text fields and buttons
        edtLRN = findViewById(R.id.edtTxtSignUpLRN);
        edtEmail = findViewById(R.id.edtTxtSignUpEmail);
        edtPass = findViewById(R.id.edtTxtSignUpPassword);
        btnSUNext = findViewById(R.id.btnSUNext);
        btnBack = findViewById(R.id.btnBackLogin);
        btnForgotPW = findViewById(R.id.btnForgotPasswd);

        // apply onClick Listener
        btnSUNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnForgotPW.setOnClickListener(this);
    }

    public static boolean validate(TextInputEditText field, Boolean pw) {
        if (!pw) {
            if (field.length() == 0) {
                field.setError("This field is required");
                return false;
            }
        } else {
            if (field.length() == 0) {
                field.setError("Password is required");
                return false;
            } else if (field.length() < 8) {
                field.setError("Password must be minimum 8 characters");
                return false;
            }
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnSUNext) {
            if (validate(edtLRN, false) && validate(edtEmail, false) && validate(edtPass, true)) {
                // start the user registration process
                fAuth.createUserWithEmailAndPassword(Objects.requireNonNull(edtEmail.getText()).toString(), Objects.requireNonNull(edtPass.getText()).toString()).addOnCompleteListener(authResult -> {
                    FirebaseUser user = fAuth.getCurrentUser();
                    if(authResult.isSuccessful()) {

                        // send verif link

                        FirebaseUser fUser = fAuth.getCurrentUser();
                        assert fUser != null;
                        fUser.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Verification email has been sent.", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Log.d("TAG", "onFailure: Email not sent " + e.getMessage()));

                        Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        assert user != null;
                        DocumentReference df = fStore.collection("Users").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("LRN", Objects.requireNonNull(edtLRN.getText()).toString());
                        userInfo.put("UserEmail", edtEmail.getText().toString());

                        // specify if the user is student/teacher/admin
                        userInfo.put("userGroup", "student");

                        df.set(userInfo);

                        startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                        finish();
                    }
                }).addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Failed to Create Account", Toast.LENGTH_SHORT).show());
            }
        }
        else if(id == R.id.btnBackLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.btnForgotPasswd) {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
            finish();
        }
    }
}