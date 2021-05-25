package com.example.userauthenticationwithimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    TextView displayEmail, displayUid;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        displayEmail = findViewById(R.id.loggedInEmail);
        displayUid = findViewById(R.id.loggedInUid);
        logout = findViewById(R.id.loggout);

//        displayEmail.setText(getIntent().getStringExtra("email").toString());
        displayEmail.setText("EMAIL ID : " +getIntent().getStringExtra("email"));
        displayUid.setText("UID is : " +getIntent().getStringExtra("uid"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
            }
        });
    }
}