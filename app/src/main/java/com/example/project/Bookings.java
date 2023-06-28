package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TimePicker;

import java.util.Locale;

public class Bookings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TabHost tabHost;
    private ImageButton back_button;
    private Button start_hours;
    private int start_hour, start_minute;
    private Button end_hours;
    private int end_hour, end_minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings);

        start_hours = findViewById(R.id.start_time);
        end_hours = findViewById(R.id.end_time);

        back_button = findViewById(R.id.back_bookings);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bookings.this, Dashboard.class);
                startActivity(intent);
            }
        });

        tabHost = findViewById(R.id.booking_tabs);
        tabHost.setup();

        // New
        TabHost.TabSpec spec = tabHost.newTabSpec("New");
        spec.setContent(R.id.New);
        spec.setIndicator("New");
        tabHost.addTab(spec);

        // Existing
        spec = tabHost.newTabSpec("Existing");
        spec.setContent(R.id.Existing);
        spec.setIndicator("Existing");

        tabHost.addTab(spec);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.libraries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void popTimePickerStart(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour1, int selectedMinute1) {
                start_hour = selectedHour1;
                start_minute = selectedMinute1;
                start_hours.setText(String.format(Locale.getDefault(), "%02d:%02d", start_hour, start_minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, start_hour, start_minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void popTimePickerEnd(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour1, int selectedMinute1) {
                end_hour = selectedHour1;
                end_minute = selectedMinute1;
                end_hours.setText(String.format(Locale.getDefault(), "%02d:%02d", end_hour, end_minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, end_hour, end_minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // setup library selected variables here
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
