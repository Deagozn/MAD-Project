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


public class SearchBooks extends AppCompatActivity {
    SearchView book_search;
    RecyclerView booklist;
    private ImageButton back_books;

    BooksAdapter adapter;
    ArrayList<Books> list;

    private static final String URL_BOOKS = "http://172.22.4.253/My Api/Api.php"; // use your own ip address; to get ip address use 'ipconfig' in command prompt

    List<Books> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_books);
        back_books = findViewById(R.id.back_books);
        book_search = findViewById(R.id.search_books);
        booklist = findViewById(R.id.book_list);
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
                            adapter = new BooksAdapter(SearchBooks.this, books);
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

}
