package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ForgetPasswordPt2 extends AppCompatActivity {

    Button resetpw;
    ImageButton back_fpass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_passwordt2);

        resetpw = findViewById(R.id.forgetpw_resetpw);

        resetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordPt2.this, LoginPage.class);
                startActivity(intent);
            }
        });
        back_fpass2=findViewById(R.id.back_forgotpass2);
        back_fpass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (ForgetPasswordPt2.this,ForgetPasswordPt1.class);
                startActivity(intent);
            }
        });
    }
}