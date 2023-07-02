package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgetPasswordPt2 extends AppCompatActivity {

    Button resetpw;

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
    }
}