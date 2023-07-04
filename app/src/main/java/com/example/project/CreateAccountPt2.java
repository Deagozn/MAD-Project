package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CreateAccountPt2 extends AppCompatActivity {

    Button createAcc;
    ImageButton back_createacc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_pt2);

        createAcc = findViewById(R.id.createAcc_setpw);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountPt2.this, LoginPage.class);
                startActivity(intent);
            }
        });
        back_createacc2=findViewById(R.id.back_create_acc2);
        back_createacc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountPt2.this, CreateAccountPt1.class);
                startActivity(intent);
            }
        });
    }
}