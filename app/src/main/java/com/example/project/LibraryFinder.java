package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LibraryFinder extends AppCompatActivity {
    private ImageView back_button;

    private CardView jurong;
    private CardView queenstown;
    private CardView bedok;
    private CardView tampines;
    private Button show_all;

    private com.example.project.LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_finder);
        back_button = findViewById(R.id.back_finder);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LibraryFinder.this,Dashboard.class);
                startActivity(intent);

            }
        });

        locationTracker = new LocationTracker(LibraryFinder.this);

        jurong = findViewById(R.id.Jurong);

        jurong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryFinder.this, MapsActivityDirection.class);
                intent.putExtra("LATITUDE", 1.333153269168847);
                intent.putExtra("LONGITUDE", 103.73954291259852);
                intent.putExtra("NAME", "Jurong Regional Library");
                startActivity(intent);
            }
        });

        queenstown = findViewById(R.id.Queenstown);

        queenstown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryFinder.this, MapsActivityDirection.class);
                intent.putExtra("LATITUDE", 1.29861379714202);
                intent.putExtra("LONGITUDE", 103.80516537993356);
                intent.putExtra("NAME", "Queenstown Public Library");
                startActivity(intent);

            }
        });

        bedok = findViewById(R.id.Bedok);

        bedok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryFinder.this, MapsActivityDirection.class);
                intent.putExtra("LATITUDE", 1.3268243723778717);
                intent.putExtra("LONGITUDE", 103.93168993455336);
                intent.putExtra("NAME", "Bedok Public Library");
                startActivity(intent);


            }
        });

        tampines = findViewById(R.id.Tampines);

        tampines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryFinder.this, MapsActivityDirection.class);
                intent.putExtra("LATITUDE", 1.3522289713983806);
                intent.putExtra("LONGITUDE", 103.9411640941522);
                intent.putExtra("NAME", "Tampines Regional Library");
                startActivity(intent);

            }
        });

        show_all = findViewById(R.id.libraries_ard);
        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryFinder.this, Maps.class);
                intent.putExtra("MYLATITUDE",locationTracker.getLatitude());
                intent.putExtra("MYLONGITUDE", locationTracker.getLongitude());
                intent.putExtra("SHOW_ALL", true);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTracker.stopUsingGPS();
    }
}