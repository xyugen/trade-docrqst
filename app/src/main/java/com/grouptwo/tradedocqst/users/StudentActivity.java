package com.grouptwo.tradedocqst.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.grouptwo.tradedocqst.AboutActivity;
import com.grouptwo.tradedocqst.request.DocReqActivity;
import com.grouptwo.tradedocqst.R;
import com.grouptwo.tradedocqst.login.LoginActivity;
import com.grouptwo.tradedocqst.request.RequestActivity;
import com.grouptwo.tradedocqst.request.RequestedActivity;

import java.util.ArrayList;
import java.util.Objects;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {
    private MainAdapter.RecyclerViewClickListener listener;

    // set elements
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button btnRqstDoc, btnResendVerif;
    ImageView imgMenu;
    String uID;
    FirebaseUser fUser;
    RecyclerView rvRequests;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter<MainAdapter.ViewHolder> mAdapter;
    ArrayList<String> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        uID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        fUser = fAuth.getCurrentUser();

        // connect elements
        btnRqstDoc = findViewById(R.id.btnRqstDoc);
        btnResendVerif = findViewById(R.id.btnVerifyEmail);
        imgMenu = findViewById(R.id.imgMenu);

        rvRequests = findViewById(R.id.rvRequests);

        // apply onClick listener
        btnRqstDoc.setOnClickListener(this);
        btnResendVerif.setOnClickListener(this);
        imgMenu.setOnClickListener(this);

        // hide resend verif if user is verified
        if (fUser.isEmailVerified()) {
            btnResendVerif.setVisibility(View.GONE);
        }

        // get requests
        requests();
    }

    /* TODO: show only unreceived docs
    private boolean showDocumentsDone(String rID) {
        Task<DocumentSnapshot> df = fStore.collection("Users")
                .document(fUser.getUid()).collection("Requests").document(rID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot ds = task.getResult();
                            if (ds.exists()){
                                boolean done = ds.getBoolean("Done");
                            }
                        }
                    }
                });
    }*/

    private void requests() {
        Task<DocumentSnapshot> df = fStore.collection("RequestIDs").document(fUser.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot ds = task.getResult();
                            if (ds.exists()){
                                    requests = (ArrayList<String>) ds.get("RequestIDs");
                                    // = ds.getBoolean("Done");
                                    assert requests != null;
                                    Log.d("RQSTd", "requests: "+requests);

                                    // recyclerview
                                    setAdapter();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("RQSTd", "error: "+e);
                    }
                });
    }

    private void setAdapter() {
        setOnClickListener();
        rvRequests.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new MainAdapter(requests, listener);
        rvRequests.setLayoutManager(mLayoutManager);
        rvRequests.setAdapter(mAdapter);
    }

    private void setOnClickListener() {
        listener = new MainAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), RequestedActivity.class);
                intent.putExtra("rID", requests.get(position));
                startActivity(intent);
            }
        };
    }

    /*private void checkUserRequests(String rID) {
        Task<DocumentSnapshot> df = fStore.collection("Users").document(fUser.getUid()).get();
        DocumentSnapshot ds = df.getResult();
        boolean done = (boolean) ds.get("Done");
        Log.d("Done", "checkUserRequests: "+done);
    }*/

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
            openMenu(v);
        }
    }

    private void openMenu(View v) {
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