package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccountPt1 extends AppCompatActivity {

    Button create_acc_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_pt1);

        create_acc_continue = findViewById(R.id.createAcc_continue);
        create_acc_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountPt1.this, CreateAccountPt2.class);
                startActivity(intent);
            }
        });
    }
}