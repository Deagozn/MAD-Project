package com.example.project;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookingSeats extends AppCompatActivity {
    ImageButton back_bookingseats;
    CardView seat1;
    CardView seat2;
    CardView seat3;
    CardView seat4;

    Button confirm;
    List<Integer> selectedSeats = new ArrayList<>();

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_seats);

        int[] seatIds = { R.id.seat1, R.id.seat2, R.id.seat3, R.id.seat4 };

        firestore.collection("seat_status").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String seatStatus = document.getString("Status");
                                int seatId = document.getLong("Seat_Number").intValue();

                                Log.d("Debug", "Seat status: " + seatStatus + ", Seat ID: " + seatId);

                                for (int i = 0; i < seatIds.length; i++) {
                                    if (seatId == i + 1) {
                                        View seatView = findViewById(seatIds[i]);
                                        if ("Available".equals(seatStatus)) {
                                            seatView.setBackgroundColor(getResources().getColor(R.color.available_seat));
                                        } else if ("Taken".equals(seatStatus)) {
                                            seatView.setBackgroundColor(getResources().getColor(R.color.taken_seat));
                                        }
                                        break;
                                    }
                                }
                            }
                        } else {
                            Log.d("Error", "Error getting collections: ", task.getException());
                        }
                    }
                });


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

        confirm = findViewById(R.id.seat_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("selectedLibrary", intent.getIntExtra("selectedLibrary", 0));
                intent.putExtra("selectedDate", intent.getStringExtra("selectedDate"));
                intent.putExtra("startTime", intent.getStringExtra("startTime"));
                intent.putExtra("endTime", intent.getStringExtra("endTime"));
                intent.putIntegerArrayListExtra("selected_seats", (ArrayList<Integer>) selectedSeats);
                Log.d("Debug", "Selected seats: " + selectedSeats);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        seat1 = findViewById(R.id.seat1);
        seat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable background = seat1.getBackground();

                if (background instanceof ColorDrawable) {
                    int currentColor = ((ColorDrawable) background).getColor();
                    if (currentColor == getResources().getColor(R.color.available_seat)) {
                        seat1.setBackgroundColor(getResources().getColor(R.color.selected_seat));
                        selectedSeats.add(1); // Add seat to selectedSeats
                    } else if (currentColor == getResources().getColor(R.color.selected_seat)) {
                        seat1.setBackgroundColor(getResources().getColor(R.color.available_seat));
                        selectedSeats.remove(Integer.valueOf(1)); // Remove seat from selectedSeats
                    }
                }

            }
        });

        seat2 = findViewById(R.id.seat2);
        seat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Drawable background = seat2.getBackground();

                if (background instanceof ColorDrawable) {
                    int currentColor = ((ColorDrawable) background).getColor();
                    if (currentColor == getResources().getColor(R.color.available_seat)) {
                        seat2.setBackgroundColor(getResources().getColor(R.color.selected_seat));
                        selectedSeats.add(2); // Add seat to selectedSeats
                    } else if (currentColor == getResources().getColor(R.color.selected_seat)) {
                        seat2.setBackgroundColor(getResources().getColor(R.color.available_seat));
                        selectedSeats.remove(Integer.valueOf(2)); // Remove seat from selectedSeats
                    }
                }

            }
        });

        seat3 = findViewById(R.id.seat3);
        seat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Drawable background = seat3.getBackground();

                if (background instanceof ColorDrawable) {
                    int currentColor = ((ColorDrawable) background).getColor();
                    if (currentColor == getResources().getColor(R.color.available_seat)) {
                        seat3.setBackgroundColor(getResources().getColor(R.color.selected_seat));
                        selectedSeats.add(3); // Add seat to selectedSeats
                    } else if (currentColor == getResources().getColor(R.color.selected_seat)) {
                        seat3.setBackgroundColor(getResources().getColor(R.color.available_seat));
                        selectedSeats.remove(Integer.valueOf(3)); // Remove seat from selectedSeats
                    }
                }

            }
        });

        seat4 = findViewById(R.id.seat4);
        seat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Drawable background = seat4.getBackground();

                if (background instanceof ColorDrawable) {
                    int currentColor = ((ColorDrawable) background).getColor();
                    if (currentColor == getResources().getColor(R.color.available_seat)) {
                        seat4.setBackgroundColor(getResources().getColor(R.color.selected_seat));
                        selectedSeats.add(4); // Add seat to selectedSeats
                    } else if (currentColor == getResources().getColor(R.color.selected_seat)) {
                        seat4.setBackgroundColor(getResources().getColor(R.color.available_seat));
                        selectedSeats.remove(Integer.valueOf(4)); // Remove seat from selectedSeats
                    }
                }


            }
        });


    }
}