package com.example.bluejackkoslab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bluejackkoslab.adapter.BookingAdapter;
import com.example.bluejackkoslab.helper.DatabaseHelperBooking;
import com.example.bluejackkoslab.model.BookedKos;

import java.util.ArrayList;
import java.util.List;

public class Booking extends AppCompatActivity {

    private ListView lvBookingList;
    private BookingAdapter adapter;
    DatabaseHelperBooking dbook;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        lvBookingList = findViewById(R.id.lv_booking_list);
        sharedPreferences = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        dbook = new DatabaseHelperBooking(this);

       final ArrayList<BookedKos> bookedKosList = dbook.ViewData(sharedPreferences.getString("id", ""));
        if(bookedKosList.isEmpty()) {
            Toast.makeText(this, "No booking transactions", Toast.LENGTH_LONG).show();
        } else {
            adapter = new BookingAdapter(this, 0, bookedKosList);
            lvBookingList.setAdapter(adapter);
        }

        lvBookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Booking.this);

                builder.setMessage("Do you want to cancel your booking?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BookedKos booking = bookedKosList.get(position);
                        String kos_Name = booking.getKosName();
                        String user_id = booking.getUserId();
                        //delete in database
                        dbook.Delete(kos_Name,user_id);
                        //delete in arraylist
                        bookedKosList.remove(booking);

                        adapter = new BookingAdapter(Booking.this, 0, bookedKosList);
                        lvBookingList.setAdapter(adapter);
                        lvBookingList.deferNotifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
