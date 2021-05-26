package com.example.userauthenticationwithimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

public class ManageOtp extends AppCompatActivity {

    EditText inputOtp;
    Button verifyOtp;
    String phnNumber;
    String otpId;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    final String TAG = "Mobile AUth";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);

        inputOtp = findViewById(R.id.enteredOtp);
        verifyOtp = findViewById(R.id.verifyAndSignin);

        phnNumber = getIntent().getStringExtra("mobile");
        Log.d("Mobile LOGIN","Mobile Number is "+phnNumber);

        mAuth = FirebaseAuth.getInstance();

        initiateOtp();

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputOtp.getText().toString().isEmpty()){
                    Toast.makeText(ManageOtp.this, "OTP Cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(inputOtp.getText().toString().length() != 6){
                    Toast.makeText(ManageOtp.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpId, inputOtp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void initiateOtp(){
//        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

//        PhoneAuthOptions.newBuilder(mAuth)
//                .setPhoneNumber(phnNumber)
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setActivity(ManageOtp.this)
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(phnNumber, 60L, TimeUnit.SECONDS, this,
//                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                        Log.v("onVerificationCompleted", phoneAuthCredential.toString());
//                        signInWithPhoneAuthCredential(phoneAuthCredential);
//                    }
//
//                    @Override
//                    public void onVerificationFailed(@NonNull FirebaseException e) {
//                        Log.v("onVerificationFailed", e.getMessage());
//                        Toast.makeText(ManageOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                        Log.v("onCodeSend", "otp is "+s);
//                        otpId = s;
//                    }
//                });

        //Another way to register user with mobile otp
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phnNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(ManageOtp.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Log.v("onVerificationCompleted", phoneAuthCredential.toString());
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.v("onVerificationFailed", e.getMessage());
                        Toast.makeText(ManageOtp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Log.v("onCodeSend", "otp is "+s);
                        otpId = s;
                    }
                }).build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            startActivity(new Intent(ManageOtp.this, MobileOptAuthenticationDashboard.class));
                            finish();

//                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(ManageOtp.this, "SignIn error", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(ManageOtp.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}