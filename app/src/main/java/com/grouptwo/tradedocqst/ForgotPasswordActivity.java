package com.grouptwo.tradedocqst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    // setting up elements
    private Button btnForgotPW, btnBack;
    private TextInputEditText edtEmail;
    private ProgressBar progressBar;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // init
        btnForgotPW = findViewById(R.id.btnResetPW);
        btnBack = findViewById(R.id.btnFPWBack);
        edtEmail = findViewById(R.id.edtTxtFPWemail);
        progressBar = findViewById(R.id.pbarFPW);

        fAuth = FirebaseAuth.getInstance();

        // on click
        btnForgotPW.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnResetPW) {
            resetPassword();
        }
        else if(id == R.id.btnFPWBack) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    private void resetPassword() {
        String email = Objects.requireNonNull(edtEmail.getText()).toString().trim();

        if(email.isEmpty()){
            edtEmail.setError("Email is required!");
            edtEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Please provide valid email!");
            edtEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }else{
                Toast.makeText(ForgotPasswordActivity.this, "Try again! SOmething wrong occured!", Toast.LENGTH_LONG).show();
            }
        });
    }
}