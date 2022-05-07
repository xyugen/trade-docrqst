package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Creating buttons
    Button btnLogin, btnSignUp, btnForgotPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String userGroup = intent.getStringExtra("user_group"); //if it's a string you stored.

        TextView txtUsrLogin = findViewById(R.id.txtUsrLogin);
        txtUsrLogin.setText(MessageFormat.format("{0}\nLogin", userGroup));

        // connecting buttons
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
            Toast toast = Toast.makeText(getApplicationContext(), "Clicked login!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(id == R.id.btnSignUp) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.btnForgotPasswd) {
            Toast toast = Toast.makeText(getApplicationContext(), "Clicked forgot pass!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}