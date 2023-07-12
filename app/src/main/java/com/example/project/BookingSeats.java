package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class BookingSeats extends AppCompatActivity {
    ImageButton back_bookingseats;

    Button confirm1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_seats);
        back_bookingseats=findViewById(R.id.back_seatBooking);
        back_bookingseats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("selectedLibrary", intent.getIntExtra("selectedLibrary", 0));
                intent.putExtra("selectedDate", intent.getStringExtra("selectedDate"));
                intent.putExtra("startTime", intent.getStringExtra("startTime"));
                intent.putExtra("endTime", intent.getStringExtra("endTime"));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        confirm1 = findViewById(R.id.seat1_confirm);
        confirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("selectedLibrary", intent.getIntExtra("selectedLibrary", 0));
                intent.putExtra("selectedDate", intent.getStringExtra("selectedDate"));
                intent.putExtra("startTime", intent.getStringExtra("startTime"));
                intent.putExtra("endTime", intent.getStringExtra("endTime"));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}