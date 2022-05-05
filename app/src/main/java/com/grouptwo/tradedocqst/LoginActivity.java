package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.MessageFormat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String value = intent.getStringExtra("user_group"); //if it's a string you stored.

        TextView txtUsrLogin = (TextView) findViewById(R.id.txtUsrLogin);
        txtUsrLogin.setText(MessageFormat.format("{0}\nLogin", value));
    }
}