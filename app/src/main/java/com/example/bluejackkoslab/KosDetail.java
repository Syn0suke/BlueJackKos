package com.example.bluejackkoslab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Calendar;

public class KosDetail extends AppCompatActivity {

    private String name,facility, address, image;
    private int price;
    private double longitude, latitude;
    private TextView kosName, kosPrice, kosFacility, kosAddress, kosLongitude, kosLatitude,tanggal;
    private NetworkImageView kosImage;
    private ImageLoader imageLoader;
    private Button booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos_detail);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        booking = findViewById(R.id.kos_book);
        kosName = findViewById(R.id.kos_name);
        kosPrice = findViewById(R.id.kos_price);
        tanggal = findViewById(R.id.tanggal);
        kosFacility = findViewById(R.id.kos_facility);
        kosAddress = findViewById(R.id.kos_address);
        kosLatitude = findViewById(R.id.kos_latitude);
        kosLongitude = findViewById(R.id.kos_longitude);
        kosImage = findViewById(R.id.kos_img);

        //get all data passed from KosList Activity
        Intent intent = getIntent();
        name = intent.getStringExtra("kos_name");
        price = intent.getIntExtra("kos_price",0);
        facility = intent.getStringExtra("kos_facility");
        address = intent.getStringExtra("kos_address");
        longitude = intent.getDoubleExtra("kos_longitude",0);
        latitude = intent.getDoubleExtra("kos_latitude",0);
        image = intent.getStringExtra("kos_image");


        //bind all data to views
        kosName.setText(name);
        kosPrice.setText("Price : Rp " + price);
        kosFacility.setText("Facilities : " + facility);
        kosAddress.setText("Address : " + address);
        kosLongitude.setText("Longitude : " + longitude);
        kosLatitude.setText("Latitude : " + latitude);
        loadImage();

    }

    private void loadImage() {
        String url = image;
        imageLoader = AppController.getInstance().getmImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(kosImage, 0, 0));
        kosImage.setImageUrl(url, imageLoader);
    }
}
