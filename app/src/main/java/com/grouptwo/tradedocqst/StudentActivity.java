package com.grouptwo.tradedocqst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    // set elements
    FirebaseAuth fAuth;
    Button btnRqstDoc;
    ImageView imgMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // firebase
        fAuth = FirebaseAuth.getInstance();

        // connect buttons
        btnRqstDoc = findViewById(R.id.btnRqstDoc);
        imgMenu = findViewById(R.id.imgMenu);

        // apply onClick listener
        btnRqstDoc.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
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
            startActivity(new Intent(this, DocReqActivity.class));
            finish();
        }
        if (id == R.id.imgMenu) {
            PopupMenu popupMenu = new PopupMenu(StudentActivity.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.logout) {
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