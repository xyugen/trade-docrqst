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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Creating buttons
    Button btnLogin, btnBackLogin, btnSignUp, btnForgotPW;
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
                fAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPass.getText().toString()).addOnSuccessListener(authResult -> {
                    Toast.makeText(LoginActivity.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                    checkUserAccessLevel(authResult.getUser().getUid());
                }).addOnFailureListener(e -> {
                });

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

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        // extract the data from the document
        df.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
            // identify the user access level

            if(Objects.equals(documentSnapshot.getString("userGroup"), "admin")) {
                // user is admin
                //startActivity(new Intent(getApplicationContext(), Admin.class));
                //finish();
            }
            if(Objects.equals(documentSnapshot.getString("userGroup"), "teacher")) {
                // user is teacher
                //startActivity(new Intent(getApplicationContext(), Teacher.class));
                //finish();
            }
            if (Objects.equals(documentSnapshot.getString("userGroup"), "student")) {
                // user is student
                startActivity(new Intent(getApplicationContext(), DocReqActivity.class));
                finish();
            }
        });
    }

    private void moveToUserSelect() {
        Intent intent = new Intent(LoginActivity.this, UserSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), StudentActivity.class));
            finish();
        }
    }
}