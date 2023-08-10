package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class BookingSeats extends AppCompatActivity {
    ImageButton back_bookingseats;
    CardView seat1;
    CardView seat2;
    CardView seat3;
    CardView seat4;

    Button confirm1;
    int selected_seat;


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

        seat1 = findViewById(R.id.seat1);
        seat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_seat = 1;
                seat1.setBackgroundColor(getResources().getColor(R.color.selected_seat));

            }
        });

        seat2 = findViewById(R.id.seat2);
        seat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_seat = 2;
                seat2.setBackgroundColor(getResources().getColor(R.color.selected_seat));

            }
        });

        seat3 = findViewById(R.id.seat3);
        seat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_seat = 3;
                seat3.setBackgroundColor(getResources().getColor(R.color.selected_seat));

            }
        });

        seat4 = findViewById(R.id.seat4);
        seat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_seat = 4;
                seat4.setBackgroundColor(getResources().getColor(R.color.selected_seat));


            }
        });


    }
}