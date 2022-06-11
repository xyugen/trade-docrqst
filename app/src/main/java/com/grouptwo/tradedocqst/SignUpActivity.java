package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // Creating buttons
    Button btnSUNext, btnBack, btnForgotPW;
    TextInputEditText edtLRN, edtEmail, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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

        // validate fields

        txtUserGroup();
    }

    private void txtUserGroup() {
        SessionManagement sessionManagement = new SessionManagement(SignUpActivity.this);
        String userGroup = sessionManagement.getGroup();
        String page = getResources().getString(R.string.c_sign_up);

        TextView txtUsrSignUp = findViewById(R.id.txtUsrSignUp);
        txtUsrSignUp.setText(MessageFormat.format("{0}\n{1}", userGroup, page));
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
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else if(id == R.id.btnBackLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.btnForgotPasswd) {
            Toast toast = Toast.makeText(getApplicationContext(), "Clicked forgot pass!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}