package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SearchBooks extends AppCompatActivity {
    private ImageButton back_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_books);
        back_books=findViewById(R.id.back_books);
        back_books.setOnClickListener(new View.OnClickListener() {
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
    }
}