package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Notifications extends AppCompatActivity {

    ImageButton back_button;
    Button clear_notif;
    LinearLayout notif_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        back_button = findViewById(R.id.back_notif);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notifications.this, Dashboard.class);
                startActivity(intent);
            }
        });

        clear_notif=findViewById(R.id.clear_notifications);
        notif_layout=findViewById(R.id.notification_layout);
        clear_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    notif_layout.removeAllViews();
                }catch (Throwable e){
                    e.printStackTrace();
                }
                TextView textView=new TextView(Notifications.this);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setText("Nothing here at the moment...");
                textView.setPadding(0,20,0,0);
                notif_layout.addView(textView);

                TextView textView1=new TextView(Notifications.this);
                textView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                textView1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView1.setText("Check again later");
                notif_layout.addView(textView1);
            }
        });

    }


}