package com.example.userauthenticationwithimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextInputLayout userEmail, userPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userEmail = findViewById(R.id.email);
        userPassword = findViewById(R.id.pwd);
        progressBar = findViewById(R.id.progressBar);

    }

    public void gotoLogin(View view) {
        startActivity(new Intent(MainActivity.this, Login.class));
    }

    public void userSignUp(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String email = userEmail.getEditText().getText().toString();
        String pass = userEmail.getEditText().getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            userEmail.getEditText().setText("");
                            userPassword.getEditText().setText("");
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressBar.setVisibility(View.INVISIBLE);
                            userEmail.getEditText().setText("");
                            userPassword.getEditText().setText("");
                            Toast.makeText(MainActivity.this, "Process Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}