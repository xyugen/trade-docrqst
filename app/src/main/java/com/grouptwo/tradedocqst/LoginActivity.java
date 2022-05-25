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
    Button btnLogin, btnBackLogin, btnSignUp, btnForgotPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connecting buttons
        btnLogin = findViewById(R.id.btnLogin);
        btnBackLogin = findViewById(R.id.btnBackLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnForgotPW = findViewById(R.id.btnForgotPasswd);

        // apply onClickListener to buttons
        btnLogin.setOnClickListener(this);
        btnBackLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnForgotPW.setOnClickListener(this);

        txtUserGroup();
    }

    private void txtUserGroup() {
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        String userGroup = sessionManagement.getGroup();

        TextView txtUsrLogin = findViewById(R.id.txtUsrLogin);
        txtUsrLogin.setText(MessageFormat.format("{0}\nLogin", userGroup));

        if(!userGroup.equals("Student")){
            btnSignUp.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);

        if(id == R.id.btnLogin) {
            Toast toast = Toast.makeText(getApplicationContext(), "Clicked login!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(id == R.id.btnBackLogin){
            sessionManagement.removeSession();

            moveToUserSelect();
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

    private void moveToUserSelect() {
        Intent intent = new Intent(LoginActivity.this, UserSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}