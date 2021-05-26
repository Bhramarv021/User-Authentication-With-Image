package com.example.userauthenticationwithimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class MobileAuthentication extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText phnNumber;
    Button getOtpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_authentication);

        phnNumber = findViewById(R.id.phnNumber);
        countryCodePicker = findViewById(R.id.ccp);
        countryCodePicker.registerCarrierNumberEditText(phnNumber);
        getOtpBtn = findViewById(R.id.getOtpButton);

        getOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MobileAuthentication.this, ManageOtp.class);
                intent.putExtra("mobile", countryCodePicker.getFullNumberWithPlus().replace(" ", ""));
                startActivity(intent);
            }
        });
    }
}