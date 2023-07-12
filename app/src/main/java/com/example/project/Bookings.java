package com.example.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Bookings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private TabHost tabHost;
    private ImageButton back_button;
    private Button start_hours;
    private int start_hour, start_minute;
    private Button end_hours;
    private int end_hour, end_minute;

    private Button select_date;

    private Button select_seat1;
    private LinearLayout no_seats_layout;
    private EditText no_of_ppl;
    private Button btn_confirm;
    private String str ="";

    private Button save;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings);

        firestore.collection("seat_status").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                // The "seat_status" collection exists
                                Log.d("Success", "Collection 'seat_status' exists.");
                            } else {
                                Log.d("Creating", "Collection 'seat_status' does not exist.");
                                // Create seat reference
                                Map<String, Object> seat = new HashMap<>();
                                seat.put("Seat_Number", 1);
                                seat.put("Status", "Available");

                                // Add a new document with a generated ID
                                firestore.collection("seat_status")
                                        .add(seat)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("Added", "DocumentSnapshot added with ID: " + documentReference.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Error", "Error adding document", e);
                                            }
                                        });

                                for(int i=1; i<4; i++) {
                                    // Create seat reference
                                    Map<String, Object> seats = new HashMap<>();
                                    seats.put("Seat_Number", i+1);
                                    seats.put("Status", "Available");

                                    // Add a new document with a generated ID
                                    firestore.collection("seat_status")
                                            .add(seats)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("Added", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("Error", "Error adding document", e);
                                                }
                                            });

                                }
                            }
                        } else {
                            Log.d("Error", "Error getting collections: ", task.getException());
                        }
                    }
                });


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

        select_date = findViewById(R.id.select_date);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Bookings.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                select_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        no_seats_layout=(LinearLayout) findViewById(R.id.no_seats_layout);
        no_of_ppl=(EditText) findViewById(R.id.no_of_ppl);
        btn_confirm=(Button) findViewById(R.id.load_no_of_seats);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(no_of_ppl.getText().toString().length()>0){
                    try{
                        no_seats_layout.removeAllViews();
                    }catch (Throwable e){
                        e.printStackTrace();
                    }
                    int length=Integer.parseInt(no_of_ppl.getText().toString());
                    for(int i=0;i<length;i++){
                        int q=i+1;
                        LinearLayout linearLayout = new LinearLayout(Bookings.this);
                        linearLayout.setId(i+100);
                        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        no_seats_layout.addView(linearLayout);

                        TextView space= new TextView(Bookings.this);
                        space.setId(i+200);
                        space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        space.setTextSize(16);
                        space.setText("           ");
                        space.setTypeface(null, Typeface.BOLD);
                        linearLayout.addView(space);

                        TextView seat_no= new TextView(Bookings.this);
                        seat_no.setId(i+200);
                        seat_no.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        seat_no.setTextSize(16);
                        seat_no.setText("Seat"+" "+q+":");
                        seat_no.setTypeface(null, Typeface.BOLD);
                        linearLayout.addView(seat_no);

                        Button select_seat = new Button(Bookings.this);
                        select_seat.setId(i+1);
                        select_seat.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        select_seat.setText("Select Seat");
                        linearLayout.addView(select_seat);

                        int buttonID=select_seat.getId();
                        select_seat1=findViewById(buttonID);

                        select_seat1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Bookings.this, BookingSeats.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });

        save = findViewById(R.id.book_seats);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
