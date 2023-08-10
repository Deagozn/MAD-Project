package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

public class BookingsAdapter extends ArrayAdapter<Map<String, Object>> {
    public BookingsAdapter(Context context, List<Map<String, Object>> bookingsList) {
        super(context, R.layout.bookings_card, bookingsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bookings_card, parent, false);
        }

        Map<String, Object> bookingData = getItem(position);

        TextView libraryTextView = convertView.findViewById(R.id.libraryNameTextView);
        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        TextView timeTextView = convertView.findViewById(R.id.timeTextView);
        TextView booksTextView = convertView.findViewById(R.id.booksTextView);
        TextView seatTextView = convertView.findViewById(R.id.seatsTextView);
        // ... Other TextViews for other data

        // Populate the TextViews with booking data
        libraryTextView.setText(bookingData.get("Library").toString());
        dateTextView.setText(bookingData.get("Date").toString());
        timeTextView.setText(bookingData.get("Start Time").toString() + " to " + bookingData.get("End Time").toString());
        booksTextView.setText(bookingData.get("Book").toString());
        seatTextView.setText(bookingData.get("Seats").toString());

        return convertView;
    }
}
