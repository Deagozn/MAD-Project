package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgetPasswordPt1 extends AppCompatActivity {

    Button cont_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_pt1);

        cont_button = findViewById(R.id.forgetpw_continue);
        cont_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordPt1.this, ForgetPasswordPt2.class);
                startActivity(intent);
            }
        });

    }
}