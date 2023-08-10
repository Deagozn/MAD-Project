package com.example.project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LoginPage extends AppCompatActivity {
    Button forgetpw;

    Button createAcc;
    ImageButton back_login;
    Button login;

    EditText email;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        dbHelper dbHelper = new dbHelper(this); // 'this' refers to the context of the activity/fragment
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AccDB.COLUMN_USERID, generateUniqueRandomUserId(db)); // You can use the method provided in the previous response
        values.put(AccDB.COLUMN_NAME, "Emily Smith");
        values.put(AccDB.COLUMN_EMAIL, "emilysmith@example.com");
        values.put(AccDB.COLUMN_PASSWORD, "SecurePass456");

        long newRowId = db.insert(AccDB.TABLE_NAME, null, values);

        forgetpw = findViewById(R.id.forget_pw);
        forgetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, ForgetPasswordPt1.class);
                startActivity(intent);
            }
        });

        createAcc = findViewById(R.id.create_acc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, CreateAccountPt1.class);
                startActivity(intent);
            }
        });
        back_login=findViewById(R.id.back_loginpage);
        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, Dashboard.class);
                startActivity(intent);
            }
        });
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = findViewById(R.id.editTextTextEmailAddress);
                password = findViewById(R.id.editTextTextPassword);

                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                // Query the database to check if the email and password match
                dbHelper dbHelper = new dbHelper(LoginPage.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                String[] projection = {
                        AccDB.COLUMN_USERID,
                        AccDB.COLUMN_NAME
                };

                String selection = AccDB.COLUMN_EMAIL + " = ? AND " +
                        AccDB.COLUMN_PASSWORD + " = ?";
                String[] selectionArgs = {userEmail, userPassword};

                Cursor cursor = db.query(
                        AccDB.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

                if (cursor.getCount() > 0) {
                    // Login successful, extract user info and navigate to dashboard
                    cursor.moveToFirst();
                    String userId = cursor.getString(cursor.getColumnIndexOrThrow(AccDB.COLUMN_USERID));
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(AccDB.COLUMN_NAME));

                    Intent intent = new Intent(LoginPage.this, Dashboard.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    // Login failed, show error toast
                    Toast.makeText(LoginPage.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
            }


            });
        };

    public int generateUniqueRandomUserId(SQLiteDatabase db) {
        Random random = new Random();
        int userId;
        boolean isUnique;

        userId = random.nextInt(900000) + 100000; // Generate a 6-digit number

        return userId;
    }
}