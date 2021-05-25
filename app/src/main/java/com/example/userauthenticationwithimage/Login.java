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

public class Login extends AppCompatActivity {

    TextInputLayout emailLogin, emailLoginPass;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        emailLogin = findViewById(R.id.loginEmail);
        emailLoginPass = findViewById(R.id.loginPwd);
        progressBar = findViewById(R.id.loginProgressBar);
    }

    public void userLogin(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String email = emailLogin.getEditText().getText().toString();
        String pass = emailLoginPass.getEditText().getText().toString();

        mAuth = FirebaseAuth.getInstance();

//        mAuth.signInWithEmailAndPassword(email, pass)
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Yipieeeeeee", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            intent.putExtra("email", mAuth.getCurrentUser().getEmail());
                            intent.putExtra("uid", mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                        }
                        else {
                            progressBar.setVisibility(View.INVISIBLE);
                            emailLogin.getEditText().setText("");
                            emailLoginPass.getEditText().setText("");
                            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}