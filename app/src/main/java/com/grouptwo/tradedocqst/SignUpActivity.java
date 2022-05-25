package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // Creating buttons
    Button btnSUNext, btnBack, btnForgotPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // connecting buttons
        btnSUNext = findViewById(R.id.btnSUNext);
        btnBack = findViewById(R.id.btnBackLogin);
        btnForgotPW = findViewById(R.id.btnForgotPasswd);

        // apply onClick Listener
        btnSUNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnForgotPW.setOnClickListener(this);

        txtUserGroup();
    }

    private void txtUserGroup() {
        SessionManagement sessionManagement = new SessionManagement(SignUpActivity.this);
        String userGroup = sessionManagement.getGroup();

        TextView txtUsrSignUp = findViewById(R.id.txtUsrSignUp);
        txtUsrSignUp.setText(MessageFormat.format("{0}\nSign Up", userGroup));

        /* SpannableString spannableString = new SpannableString((CharSequence) txtUsrSignUp);
        spannableString.setSpan(
                new RelativeSizeSpan(1.2f), txtUsrSignUp.length() -7, txtUsrSignUp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        txtUsrSignUp.setText(spannableString); */
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnSUNext) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
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