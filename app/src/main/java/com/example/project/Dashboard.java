package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;


import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Button notif_button;
    Button add_booking;



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
            startActivity(intent);

        }else if(item.getItemId()==R.id.nav_seat_map){
            Intent intent= new Intent (Dashboard.this,SeatMap.class);
            startActivity(intent);}
        return true;
    }


}