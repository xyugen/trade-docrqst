package com.grouptwo.tradedocqst.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.grouptwo.tradedocqst.AboutActivity;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.login.LoginActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    // setting up elements
    Button btnCreateAcc;
    ImageView imgMenu;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        // connect buttons
        btnCreateAcc = findViewById(R.id.btnCreateAccount);
        imgMenu = findViewById(R.id.imgMenu);

        // apply onClick listener
        btnCreateAcc.setOnClickListener(this);
        imgMenu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCreateAccount) {
            startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
            finish();
        }
        else if (id == R.id.imgMenu) {
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
}