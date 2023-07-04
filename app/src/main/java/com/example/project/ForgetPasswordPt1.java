package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ForgetPasswordPt1 extends AppCompatActivity {

    Button cont_button;
    ImageButton back_fpass1;

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
        back_fpass1=findViewById(R.id.back_forgotpass1);
        back_fpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ForgetPasswordPt1.this,LoginPage.class);
                startActivity(intent);
            }
        });
    }
}