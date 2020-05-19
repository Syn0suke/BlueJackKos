package com.example.bluejackkoslab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bluejackkoslab.R;
import com.example.bluejackkoslab.model.BookedKos;

import java.util.ArrayList;


public class BookingAdapter extends ArrayAdapter<BookedKos> {
    private  Context context;
    private ArrayList<BookedKos> bookedKosList;

    public BookingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BookedKos> bookedKosList) {
        super(context, resource, bookedKosList);
        this.context = context;
        this.bookedKosList = bookedKosList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.booking_info,parent,false);
        }

        TextView bookingId = convertView.findViewById(R.id.booking_id);
        TextView userId = convertView.findViewById(R.id.user_id);
        TextView kosName = convertView.findViewById(R.id.kos_name);
        TextView kosFacility = convertView.findViewById(R.id.kos_facility);
        TextView kosPrice = convertView.findViewById(R.id.kos_price);
        TextView kosAddress = convertView.findViewById(R.id.kos_address);
        TextView kosLongitude = convertView.findViewById(R.id.kos_longitude);
        TextView kosLatitude = convertView.findViewById(R.id.kos_latitude);
        TextView bookingDate = convertView.findViewById(R.id.booking_date);

        bookingId.setText(bookedKosList.get(position).getBookingId());
        userId.setText(bookedKosList.get(position).getUserId());
        kosName.setText(bookedKosList.get(position).getKosName());
        kosFacility.setText(bookedKosList.get(position).getKosFacility());
        kosPrice.setText(bookedKosList.get(position).getKosPrice());
        kosAddress.setText(bookedKosList.get(position).getKosAddress());
        kosLongitude.setText(bookedKosList.get(position).getKosLongitude());
        kosLatitude.setText(bookedKosList.get(position).getKosLatitude());
        bookingDate.setText(bookedKosList.get(position).getBookingDate());

        return convertView;
    }
}
