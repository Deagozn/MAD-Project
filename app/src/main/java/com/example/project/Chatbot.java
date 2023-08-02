package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class Chatbot extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessageRVAdapter messageAdapter;
    private ArrayList<MessageModal> messageList;

    private ImageView back_chatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot);

        recyclerView = findViewById(R.id.idRVChats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the message list
        messageList = new ArrayList<>();

        // Set up the adapter with the message list
        messageAdapter = new MessageRVAdapter(messageList, this);
        recyclerView.setAdapter(messageAdapter);

        // Add some initial messages (You can remove this if not needed)
        messageList.add(new MessageModal("Hi, I'm your chatbot. How can I help you?", "bot"));

        // Notify the adapter that the data has changed
        messageAdapter.notifyDataSetChanged();


        back_chatbot = findViewById(R.id.back_chatbot);
        back_chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Chatbot.this,Dashboard.class);
                startActivity(intent);
            }
        });

        ImageButton imageButtonSubmit = findViewById(R.id.idIBSend);
        imageButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    public void sendMessage() {
        // Get the user input message from the input field (assuming you have an EditText for user input)
        EditText userInputEditText = findViewById(R.id.idEdtMessage);
        String userMessage = userInputEditText.getText().toString().trim();

        // Add the user message to the message list
        messageList.add(new MessageModal(userMessage, "user"));

        // Clear the input field
        userInputEditText.setText("");

        // Notify the adapter that the data has changed
        messageAdapter.notifyDataSetChanged();

        // Process user input and generate bot response
        if (userMessage.equalsIgnoreCase("What is your name?")) {
            // If the user asks for the chatbot's name, add the bot response to the message list
            messageList.add(new MessageModal("My name is Kiasu Librarian.", "bot"));
        } else if (userMessage.equalsIgnoreCase("What do you do?")) {
            messageList.add(new MessageModal("I can answer your questions about the app.", "bot"));
        } else if (userMessage.equalsIgnoreCase("How can i use the app?")) {
            messageList.add(new MessageModal("You can use the app to find libraries around you, book seats, check seat availability and much more.", "bot"));
        } else if (userMessage.equalsIgnoreCase("How do i book a seat?")) {
            messageList.add(new MessageModal("ou can book a seat by opening the menu, and click bookings. You then can select your which library to book for, when, and which seat you want. You also have the option to reserve a book together with your seat.", "bot"));
        } else if (userMessage.equalsIgnoreCase("Hello, I have a question.")) {
            messageList.add(new MessageModal("Sure, how can I help you?", "bot"));
        } else {
            // For any other user input, add a generic response
            messageList.add(new MessageModal("I'm sorry, I don't understand that.", "bot"));
        }

        // Notify the adapter that the data has changed
        messageAdapter.notifyDataSetChanged();

        recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
    }
}