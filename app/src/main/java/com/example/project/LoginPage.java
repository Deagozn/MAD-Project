package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginPage extends AppCompatActivity {
    Button forgetpw;

    Button createAcc;
    ImageButton back_login;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        forgetpw = findViewById(R.id.forget_pw);
        forgetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, ForgetPasswordPt1.class);
                startActivity(intent);
            }
        });

        createAcc = findViewById(R.id.create_acc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, CreateAccountPt1.class);
                startActivity(intent);
            }
        });
        back_login=findViewById(R.id.back_loginpage);
        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, Dashboard.class);
                startActivity(intent);
            }
        });
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LoginPage.this,Dashboard.class);
                startActivity(intent);
            }
        });
    }
}