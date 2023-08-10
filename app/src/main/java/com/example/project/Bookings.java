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
import android.widget.ListView;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    private Spinner spinner;

    private Button save;

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_SEARCH_BOOK = 1;

    private int selectedLibrary;
    private String selectedDate;
    private String startTime;
    private String endTime;
    private TextView selectedBook;

    private String bookSelected;

    private int selectedLibrary2;
    private String selectedDate2;
    private String startTime2;
    private String endTime2;

    private Button addbooks;

    private Button selectSeats;
    private TextView textViewSelectedSeats;

    private ListView existingListView;
    private List<Map<String, Object>> bookingsList = new ArrayList<>();
    private BookingsAdapter bookingsAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookings);
        addbooks = findViewById(R.id.add_book);
        selectedBook = findViewById(R.id.selectedBook);
        textViewSelectedSeats = findViewById(R.id.selected_seats);

        // Get the data sent from SearchBooks activity
        Intent intent = getIntent();
        if (intent != null) {
            String bookName = intent.getStringExtra("book_name");
            int selectedLibrary = intent.getIntExtra("selectedLibrary", 0);
            String selectedDate = intent.getStringExtra("selectedDate");
            String startTime = intent.getStringExtra("startTime");
            String endTime = intent.getStringExtra("endTime");


            // Now you have the data, you can use it as needed in the Bookings activity.
            // For example, display the bookName in a TextView
            TextView textViewBookName = findViewById(R.id.selectedBook);
            textViewBookName.setText(bookName);


        }

        existingListView = findViewById(R.id.booking_list); // Add this ListView in your XML layout

        // Initialize the adapter with the bookingsList
        bookingsAdapter = new BookingsAdapter(this, bookingsList);

        // Set the adapter to the ListView
        existingListView.setAdapter(bookingsAdapter);

        // Populate the bookingsList with data from Firestore
        populateBookingsList();

        addbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Bookings.this,SearchBooks.class);
                startActivityForResult(intent, REQUEST_CODE_SEARCH_BOOK);
            }
        });

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


        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.libraries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_hours = findViewById(R.id.start_time);
        end_hours = findViewById(R.id.end_time);
        select_date = findViewById(R.id.select_date);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.books, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



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



        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLibrary = spinner.getSelectedItemPosition();
                Log.d("check", String.valueOf(selectedLibrary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                                selectedDate = select_date.getText().toString();
                                Log.d("check", selectedDate);

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

        selectSeats = findViewById(R.id.select_seats);
        selectSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bookings.this, BookingSeats.class);
                // Pass the selected information to the BookingSeats activity
                intent.putExtra("selectedLibrary", selectedLibrary);
                intent.putExtra("selectedDate", selectedDate);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        save = findViewById(R.id.book_seats);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extract seat numbers from the formatted string
                String selectedSeatsString = textViewSelectedSeats.getText().toString();
                String[] seatNumberStrings = selectedSeatsString.split(", ");
                List<Integer> selectedSeatNumbers = new ArrayList<>();

                for (String seatNumberString : seatNumberStrings) {
                    String number = seatNumberString.replace("seat", "");
                    selectedSeatNumbers.add(Integer.parseInt(number));
                }
                // Update seat status for selected seats
                for (Integer seatNumber : selectedSeatNumbers) {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("Status", "Taken");

                    firestore.collection("seat_status")
                            .whereEqualTo("Seat_Number", seatNumber)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                        String documentId = documentSnapshot.getId();

                                        firestore.collection("seat_status").document(documentId)
                                                .update(updates)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Seat status updated successfully
                                                        Log.d("Debug", "Seat status updated for seat " + seatNumber);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e("Error", "Error updating seat status for seat " + seatNumber, e);
                                                    }
                                                });
                                    }
                                }
                            });
                }

                // Get the selected library from the spinner
                Spinner spinner = findViewById(R.id.spinner); // Replace with your Spinner's ID
                int selectedLibraryPosition = spinner.getSelectedItemPosition();
                CharSequence selectedLibraryName = adapter.getItem(selectedLibraryPosition); // Assuming you have the 'adapter' defined

                // Create a new booking entry
                Map<String, Object> bookingData = new HashMap<>();
                bookingData.put("Library", selectedLibraryName.toString());
                bookingData.put("Date", select_date.getText().toString());
                bookingData.put("Start Time", start_hours.getText().toString());
                bookingData.put("End Time", end_hours.getText().toString());
                bookingData.put("Book", selectedBook.getText().toString());
                bookingData.put("Seats", textViewSelectedSeats.getText().toString());

                firestore.collection("bookings")
                        .add(bookingData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("Debug", "Booking added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Error", "Error adding booking", e);
                            }
                        });
                tabHost.setCurrentTab(1);
            }
        });

        String bookName = getIntent().getStringExtra("book_name");
        selectedBook.setText(bookName);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if ("Existing".equals(tabId)) {
                    // Refresh the "Existing" tab content when tab is changed
                    refreshExistingTab();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                // Restore the selected information from the BookingSeats activity
                selectedLibrary2 = data.getIntExtra("selectedLibrary", 0);
                selectedDate2 = data.getStringExtra("selectedDate");
                startTime2 = data.getStringExtra("startTime");
                endTime2 = data.getStringExtra("endTime");
                ArrayList<Integer> selectedSeats = data.getIntegerArrayListExtra("selected_seats");
                Log.d("Debug", "Selected seats: " + selectedSeats);

                spinner.setSelection(selectedLibrary);
                end_hours.setText(endTime);
                start_hours.setText(startTime);
                select_date.setText(selectedDate);
                selectedBook.setText(bookSelected);



                if (selectedSeats != null && !selectedSeats.isEmpty()) {
                    Collections.sort(selectedSeats); // Sort the selectedSeats list in ascending order
                    StringBuilder selectedSeatsText = new StringBuilder();

                    for (Integer seatNumber : selectedSeats) {
                        selectedSeatsText.append("seat").append(seatNumber).append(", ");
                    }

                    // Remove the trailing comma and space
                    selectedSeatsText.delete(selectedSeatsText.length() - 2, selectedSeatsText.length());

                    textViewSelectedSeats.setText(selectedSeatsText.toString());
                } else {
                    textViewSelectedSeats.setText("No seats selected");
                }
            }
        }
        if (requestCode == REQUEST_CODE_SEARCH_BOOK) {
            if (resultCode == RESULT_OK && data != null) {
                // Get the selected book name from the SearchBooks activity
                selectedLibrary2 = data.getIntExtra("selectedLibrary", 0);
                selectedDate2 = data.getStringExtra("selectedDate");
                startTime2 = data.getStringExtra("startTime");
                endTime2 = data.getStringExtra("endTime");
                bookSelected = data.getStringExtra("book_name");

                // Now you can use the bookName as needed
                // For example, update the selectedBook TextView
                spinner.setSelection(selectedLibrary);
                end_hours.setText(endTime);
                start_hours.setText(startTime);
                select_date.setText(selectedDate);
                selectedBook.setText(bookSelected);
            }
        }
    }

    public void popTimePickerStart(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour1, int selectedMinute1) {
                start_hour = selectedHour1;
                start_minute = selectedMinute1;
                start_hours.setText(String.format(Locale.getDefault(), "%02d:%02d", start_hour, start_minute));
                startTime = start_hours.getText().toString();
                Log.d("Check", startTime);
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
                endTime = end_hours.getText().toString();
                Log.d("Check", endTime);
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

    // Method to populate the bookingsList with data from Firestore
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
    private void refreshExistingTab() {
        // Populate the bookingsList with data from Firestore for the "Existing" tab
        populateBookingsList();

        // Update the bookingsAdapter and notify it about the data change
        bookingsAdapter.notifyDataSetChanged();
    }



}


