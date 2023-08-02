package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {
    private Context mCtx;
    private List<Books> booksList;
    private List<Books> booksListFull;
    private ArrayList<Books> booksArrayList;
    ;

    public BooksAdapter(Context mCtx, List<Books> booksList) {
        this.mCtx = mCtx;
        this.booksList = booksList;
        this.booksListFull = new ArrayList<>(booksList);
    }

    public void filterList(ArrayList<Books> filterlist) {
//        // below line is to add our filtered
//        // list in our course array list.
//        booksArrayList = filterlist;
//        // below line is to notify our adapter
//        // as change in recycler view data.
//        notifyDataSetChanged();
        booksList.clear();
        booksList.addAll(filterlist);
        notifyDataSetChanged();
    }

    public void resetList() {
        booksList.clear();
        booksList.addAll(booksListFull);
        notifyDataSetChanged();
    }

    @Override
    public BooksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.book_list, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BooksViewHolder holder, int position) {
        Books books = booksList.get(position);
        holder.textViewName.setText(books.getBookName());

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;

        public BooksViewHolder(View itemView) {
            super(itemView);

            // Initialize the TextView
            textViewName = itemView.findViewById(R.id.textViewName);

            // set the OnClickListener on the view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the clicked book's name
            String bookName = booksList.get(getAdapterPosition()).getBookName();

            // Create an Intent to start the BookDetailsActivity
            Intent intent = new Intent(mCtx, Bookings.class);
            intent.putExtra("book_name", bookName); // Pass the book name as an extra

            // Start the BookDetailsActivity
            mCtx.startActivity(intent);

        }

    }
}
