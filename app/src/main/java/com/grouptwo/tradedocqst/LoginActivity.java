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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Creating buttons
    Button btnLogin, btnBackLogin, btnSignUp, btnForgotPW;
    TextInputEditText edtEmail, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connecting fields & buttons
        edtEmail = findViewById(R.id.edtTxtLoginEmail);
        edtPass = findViewById(R.id.edtTxtLoginPassword);
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
        String page = getResources().getString(R.string.c_login);

        TextView txtUsrLogin = findViewById(R.id.txtUsrLogin);
        txtUsrLogin.setText(MessageFormat.format("{0}\n{1}", userGroup, page));

        if(!userGroup.equals("Student")){
            btnSignUp.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);

        if(id == R.id.btnLogin) {
            if (SignUpActivity.validate(edtEmail, false) && SignUpActivity.validate(edtPass, true)) {
                Intent intent = new Intent(this, DocReqActivity.class);
                startActivity(intent);
                finish();
            }
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