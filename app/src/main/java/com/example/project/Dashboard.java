package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Button notif_button;
    Button add_booking;
    ListView bookingdash;
    TextView username;
    TextView id;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private BookingsAdapter bookingsAdapter;
    private List<Map<String, Object>> bookingsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        add_booking=findViewById(R.id.add_booking);
        notif_button = findViewById(R.id.notif_button);
        add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Bookings.class);
                startActivity(intent);

            }
        });

        notif_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Notifications.class);
                startActivity(intent);

            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        bookingdash = findViewById(R.id.bookingsdashboard); // Add this ListView in your XML layout

        // Initialize the adapter with the bookingsList
        bookingsAdapter = new BookingsAdapter(this, bookingsList);

        // Set the adapter to the ListView
        bookingdash.setAdapter(bookingsAdapter);

        populateBookingsList();

        username = findViewById(R.id.username);
        id = findViewById(R.id.iduser);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_about) {
            Intent intent = new Intent(Dashboard.this, About.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_bookings) {
            Intent intent = new Intent(Dashboard.this, Bookings.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_finder) {
            Intent intent = new Intent(Dashboard.this, LibraryFinder.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_settings) {
            Intent intent=new Intent(Dashboard.this, AccountSettings.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.nav_feedback) {
            Intent intent=new Intent(Dashboard.this, Feedback.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.change_user) {
            Intent intent=new Intent(Dashboard.this, LoginPage.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logout) {
            finishAffinity();
            System.exit(0);
        } else if(item.getItemId() == R.id.nav_qr_code) {
            Intent intent=new Intent(Dashboard.this, QRCode.class);
            intent.putExtra("username", username.getText().toString());
            Log.d("debug", username.getText().toString());
            intent.putExtra("id", id.getText().toString());
            Log.d("debug", id.getText().toString());
            startActivity(intent);

        } else if(item.getItemId()==R.id.nav_seat_map){
            Intent intent= new Intent (Dashboard.this,SeatMap.class);
            startActivity(intent);
        } else if(item.getItemId()==R.id.nav_chatbot){
        Intent intent= new Intent (Dashboard.this,Chatbot.class);
        startActivity(intent);}

        return true;
    }

    private void populateBookingsList() {
        firestore.collection("bookings").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            bookingsList.clear(); // Clear the list before adding new data

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Extract booking information and create a Map
                                Map<String, Object> bookingData = new HashMap<>();
                                bookingData.put("Library", document.getString("Library"));
                                bookingData.put("Date", document.getString("Date"));
                                bookingData.put("Start Time", document.getString("Start Time"));
                                bookingData.put("End Time", document.getString("End Time"));
                                bookingData.put("Book", document.getString("Book"));
                                bookingData.put("Seats", document.getString("Seats"));

                                // Add the Map to the bookingsList
                                bookingsList.add(bookingData);
                            }

                            // Notify the adapter that data has changed
                            bookingsAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("Error", "Error getting bookings: ", task.getException());
                        }
                    }
                });
        LinearLayout existingLayout = findViewById(R.id.Existing); // Replace with your existing layout's ID
    }


}