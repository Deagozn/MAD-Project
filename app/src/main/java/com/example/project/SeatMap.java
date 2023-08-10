package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SeatMap extends AppCompatActivity {
    private ImageButton back_seatmap;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_map);
        back_seatmap= findViewById(R.id.back_seatMap);
        back_seatmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SeatMap.this,Dashboard.class);
                startActivity(intent);
            }
        });
        int[] seatIds = { R.id.mseat1, R.id.mseat2, R.id.mseat3, R.id.mseat4 };

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
    }
}