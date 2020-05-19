package com.example.bluejackkoslab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bluejackkoslab.helper.DatabaseHelperBooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KosDetail extends AppCompatActivity {

     DatabaseHelperBooking dbook;
    private String name,facility, address, image;
    private int price;
    private double longitude, latitude;
    private TextView kosName, kosPrice, kosFacility, kosAddress, kosLongitude, kosLatitude,tanggal;
    private NetworkImageView kosImage;
    private ImageLoader imageLoader;
    private Button booking,location;
    DatePickerDialog dialog;
    SharedPreferences sharedPreferences, locationPreferences;
    SharedPreferences.Editor editor, leditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos_detail);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        dbook = new DatabaseHelperBooking(KosDetail.this);
        booking = findViewById(R.id.kos_book);
        location = findViewById(R.id.view_location);
        kosName = findViewById(R.id.kos_name);
        kosPrice = findViewById(R.id.kos_price);
        tanggal = findViewById(R.id.tanggal);
        kosFacility = findViewById(R.id.kos_facility);
        kosAddress = findViewById(R.id.kos_address);
        kosLatitude = findViewById(R.id.kos_latitude);
        kosLongitude = findViewById(R.id.kos_longitude);
        kosImage = findViewById(R.id.kos_img);
        sharedPreferences = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        locationPreferences = getSharedPreferences("locationdata", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        leditor = locationPreferences.edit();

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Kos_Longitude = kosLongitude.getText().toString().trim();
                String Kos_Latitude = kosLatitude.getText().toString().trim();
                leditor.putString("longitude",Kos_Longitude);
                leditor.putString("latitude",Kos_Latitude);
                leditor.commit();
                Intent intent = new Intent(KosDetail.this,Location.class);
                startActivity(intent);
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new DatePickerDialog(KosDetail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tanggal.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        if (valdate(tanggal) && valsame(kosName)) {
                            String Kosname = kosName.getText().toString().trim();
                            String Kosprice = kosPrice.getText().toString().trim();
                            String KosFacility = kosFacility.getText().toString().trim();
                            String KosAddress = kosAddress.getText().toString().trim();
                            String KosLongitude = kosLongitude.getText().toString().trim();
                            String KosLatitude = kosLatitude.getText().toString().trim();
                            String Tanggal = tanggal.getText().toString().trim();
                            String iduser = sharedPreferences.getString("id", "");
                            int num = dbook.Count() + 1;
                            String code = String.format("%03d", num);
                            String bookId = "BK" + code;

                            dbook.addData(bookId,iduser,Kosname,Kosprice,KosFacility,KosAddress,KosLongitude,KosLatitude,Tanggal);

                            Intent intent = new Intent(KosDetail.this, Booking.class);
                            startActivity(intent);

                        }

                    }
                },year,month,day);
                dialog.show();
            }
        });

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
        kosPrice.setText(price+"");
        kosFacility.setText( facility);
        kosAddress.setText(address);
        kosLongitude.setText(longitude+"");
        kosLatitude.setText(latitude+"");
        loadImage();

    }

    private void loadImage() {
        String url = image;
        imageLoader = AppController.getInstance().getmImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(kosImage, 0, 0));
        kosImage.setImageUrl(url, imageLoader);
    }

    private boolean valdate(TextView Tanggal)
    {
        Date selectedDate;
        try {
            selectedDate = new SimpleDateFormat("dd/MM/yyyy").parse(Tanggal.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        Calendar now = Calendar.getInstance();
        int currentYear  = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH)+1;
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);
        int selectedYear = selectedCal.get(Calendar.YEAR);
        int selectedMonth = selectedCal.get(Calendar.MONTH)+1;
        int selectedDay = selectedCal.get(Calendar.DAY_OF_MONTH);


        if(selectedYear < currentYear){
            Toast.makeText(this,"Date cannot less than today",Toast.LENGTH_LONG).show();
            return false;
        }

        if(selectedYear == currentYear){
            if(selectedMonth < currentMonth){
                Toast.makeText(this,"Date cannot less than today",Toast.LENGTH_LONG).show();
                return false;
            }

            if(selectedMonth == currentMonth){
                if(selectedDay < currentDay){
                    Toast.makeText(this,"Date cannot less than today",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
    }

    private boolean valsame(TextView kosname)
    {
        String namkos = kosname.getText().toString();
        int value = 0;

        if (dbook.checkKos(namkos,sharedPreferences.getString("id",""))) {
            value = 1;
        }

        if(value == 1)
        {
            Toast.makeText(this, "Can't reserve same kos twice", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }
    }
}
