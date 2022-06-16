package com.grouptwo.tradedocqst.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.tradedocqst.users.AdminActivity;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.users.StudentActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Creating buttons
    Button btnLogin, btnSignUp, btnForgotPW;
    TextInputEditText edtEmail, edtPass;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // connecting fields & buttons
        edtEmail = findViewById(R.id.edtTxtLoginEmail);
        edtPass = findViewById(R.id.edtTxtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnForgotPW = findViewById(R.id.btnForgotPasswd);

        // apply onClickListener to buttons
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnForgotPW.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnLogin) {
            if (SignUpActivity.validateEmail(edtEmail) && SignUpActivity.validate(edtPass, true)) {
                fAuth.signInWithEmailAndPassword(Objects.requireNonNull(edtEmail.getText()).toString(), Objects.requireNonNull(edtPass.getText()).toString())
                        .addOnSuccessListener(authResult -> {
                    Toast.makeText(LoginActivity.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                    checkUserAccessLevel(Objects.requireNonNull(authResult.getUser()).getUid());
                }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Log in failed.", Toast.LENGTH_SHORT).show());
            }
        }
        else if(id == R.id.btnSignUp) {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        }
        else if(id == R.id.btnForgotPasswd) {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
            finish();
        }
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        // extract the data from the document
        df.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
            // identify the user access level

            if(Objects.equals(documentSnapshot.getString("userGroup"), "admin")) {
                // user is admin
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();
            }
            if(Objects.equals(documentSnapshot.getString("userGroup"), "teacher")) {
                // user is teacher
                //startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
                //finish();
            }
            if (Objects.equals(documentSnapshot.getString("userGroup"), "student")) {
                // user is student
                startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser() != null) {
            checkUserAccessLevel(fAuth.getCurrentUser().getUid());
            finish();
        }
    }
}