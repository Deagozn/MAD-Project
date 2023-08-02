package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchBooks extends AppCompatActivity implements BooksAdapter.OnItemClickListener{
    SearchView book_search;
    RecyclerView booklist;
    private ImageButton back_books;

    BooksAdapter adapter;
    ArrayList<Books> list;

    private Integer selectedLibrary;
    private String selectedDate;
    private String startTime;
    private String endTime;


    private static final String URL_BOOKS = "http://172.22.4.253/My Api/Api.php"; // use your own ip address; to get ip address use 'ipconfig' in command prompt

    List<Books> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_books);
        back_books = findViewById(R.id.back_books);
        book_search = findViewById(R.id.search_books);
        booklist = findViewById(R.id.book_list);

        Intent intent = new Intent();
        selectedLibrary = intent.getIntExtra("selectedLibrary", 0);
        selectedDate = intent.getStringExtra("selectedDate");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        back_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("selectedLibrary", selectedLibrary);
                intent.putExtra("selectedDate", selectedDate);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        booklist.setHasFixedSize(true);
        booklist.setLayoutManager(new LinearLayoutManager(this));

        books = new ArrayList<>();

        loadBooks();

        book_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter.resetList(); // Call this method to reset the list when the query is empty
                } else {
                    filter(newText);
                }
                return true;
            }
        });
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Books> filteredlist = new ArrayList<Books>();

        // running a for loop to compare elements.
        for (Books item : books) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getBookName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }


    private void loadBooks() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BOOKS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                books.add(new Books(
                                        product.getInt("id"),
                                        product.getString("name")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            adapter = new BooksAdapter(SearchBooks.this, books, SearchBooks.this);
                            booklist.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemClick(String bookName) {
        // Handle the item click event here
        // You will receive the clicked book name in this method
        // You can also access other data from the SearchBooks activity as needed
        // Example: To display a toast message with the book name:
        Toast.makeText(this, "Clicked book: " + bookName, Toast.LENGTH_SHORT).show();

        // You can start the Bookings activity with the book name and other data here
        Intent intent = new Intent();
        intent.putExtra("book_name", bookName);
        intent.putExtra("selectedLibrary", selectedLibrary );
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", endTime);
        setResult(RESULT_OK, intent);
        finish();
    }

}
