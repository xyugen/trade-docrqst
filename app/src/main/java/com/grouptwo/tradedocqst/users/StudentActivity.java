package com.grouptwo.tradedocqst.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grouptwo.tradedocqst.AboutActivity;
import com.grouptwo.tradedocqst.request.DocReqActivity;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.login.LoginActivity;

import java.util.Objects;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    // set elements
    FirebaseAuth fAuth;
    Button btnRqstDoc, btnResendVerif;
    ImageView imgMenu;
    String uID;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        uID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        fUser = fAuth.getCurrentUser();

        // connect buttons
        btnRqstDoc = findViewById(R.id.btnRqstDoc);
        btnResendVerif = findViewById(R.id.btnVerifyEmail);
        imgMenu = findViewById(R.id.imgMenu);

        // apply onClick listener
        btnRqstDoc.setOnClickListener(this);
        btnResendVerif.setOnClickListener(this);
        imgMenu.setOnClickListener(this);

        // hide resend verif if user is verified
        if (fUser.isEmailVerified()) {
            btnResendVerif.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnRqstDoc) {
            if(Objects.requireNonNull(fUser).isEmailVerified()){
                startActivity(new Intent(this, DocReqActivity.class));
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Email not verified.", Toast.LENGTH_SHORT).show();
            }
        }else if (id == R.id.btnVerifyEmail) {
            fUser.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Verification email has been sent.", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Log.d("TAG", "onFailure: Email not sent " + e.getMessage()));
        }else if (id == R.id.imgMenu) {
            PopupMenu popupMenu = new PopupMenu(StudentActivity.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.profile){
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
                else if (menuItem.getItemId() == R.id.about) {
                    startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                }
                else if (menuItem.getItemId() == R.id.logout) {
                    fAuth.signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        }
    }
}