package com.grouptwo.tradedocqst.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.tradedocqst.AboutActivity;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.login.LoginActivity;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {

    // setting elements
    ImageView imgMenu;
    Button btnResendVerif;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        // connecting elements
        imgMenu = findViewById(R.id.imgMenu);
        btnResendVerif = findViewById(R.id.btnVerifyEmail);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = fAuth.getCurrentUser();

        // applying onclick listener
        imgMenu.setOnClickListener(this);
        btnResendVerif.setOnClickListener(this);

        // hide resend verif if user is verified
        if (fUser.isEmailVerified()) {
            btnResendVerif.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnVerifyEmail){
            fUser.sendEmailVerification()
                    .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Verification email has been sent.", Toast.LENGTH_LONG).show())
                    .addOnFailureListener(e -> Log.d("TAG", "onFailure: Email not sent " + e.getMessage()));
        } else if (id == R.id.imgMenu){
            openMenu(view);
        }
    }

    private void openMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
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