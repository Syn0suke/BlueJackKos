package com.example.bluejackkoslab.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.bluejackkoslab.model.BookedKos;

import java.util.ArrayList;

public class DatabaseHelperBooking extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Booking.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "user_booking";
    private static final String COLUMN_ID_BOOK = "booking_id";
    private static final String COLUMN_ID_USER = "user_id";
    private static final String COLUMN_KOS_NAME = "kos_name";
    private static final String COLUMN_KOS_PRICE = "kos_price";
    private static final String COLUMN_KOS_FACILITY = "kos_facility";
    private static final String COLUMN_KOS_ADDRESS = "kos_address";
    private static final String COLUMN_KOS_LONGITUDE = "kos_longitude";
    private static final String COLUMN_KOS_LATITUDE = "kos_latitude";
    private static final String COLUMN_BOOK_DATE = "kos_booking_date";

    public DatabaseHelperBooking(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID_BOOK + " TEXT PRIMARY KEY NOT NULL, " +
                        COLUMN_ID_USER + " TEXT NOT NULL, " +
                        COLUMN_KOS_NAME + " TEXT NOT NULL, " +
                        COLUMN_KOS_PRICE + " TEXT NOT NULL, " +
                        COLUMN_KOS_FACILITY + " TEXT NOT NULL, " +
                        COLUMN_KOS_ADDRESS + " TEXT NOT NULL, " +
                        COLUMN_KOS_LONGITUDE + " TEXT NOT NULL, " +
                        COLUMN_KOS_LATITUDE + " TEXT NOT NULL, " +
                        COLUMN_BOOK_DATE + " TEXT NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(String book_id, String user_id, String kos_name, String price, String facility, String address, String longitude, String latitude, String booking_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cval = new ContentValues();

        cval.put(COLUMN_ID_BOOK, book_id);
        cval.put(COLUMN_ID_USER, user_id);
        cval.put(COLUMN_KOS_NAME, kos_name);
        cval.put(COLUMN_KOS_PRICE, price);
        cval.put(COLUMN_KOS_FACILITY, facility);
        cval.put(COLUMN_KOS_ADDRESS, address);
        cval.put(COLUMN_KOS_LONGITUDE, longitude);
        cval.put(COLUMN_KOS_LATITUDE, latitude);
        cval.put(COLUMN_BOOK_DATE, booking_date);
        long result = db.insert(TABLE_NAME, null, cval);
        if (result == -1) {
            Toast.makeText(context, "Booking Failed", Toast.LENGTH_LONG).show();
            db.close();
        } else {
            Toast.makeText(context, "Booking successful", Toast.LENGTH_LONG).show();
            db.close();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public int Count() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public boolean checkKos(String kos_name, String user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where kos_name=? AND user_id =?", new String[]{kos_name,user_id});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public ArrayList<BookedKos> ViewData(String idUser) {
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE "+COLUMN_ID_USER+" LIKE '"+idUser+"'";
        ArrayList<BookedKos> bookedKosList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
            while(cursor.moveToNext()){
                String BookingId = cursor.getString(cursor.getColumnIndex(COLUMN_ID_BOOK));
                String UserId = cursor.getString(cursor.getColumnIndex(COLUMN_ID_USER));
                String kosName = cursor.getString(cursor.getColumnIndex(COLUMN_KOS_NAME));
                String kosPrice = cursor.getString(cursor.getColumnIndex(COLUMN_KOS_PRICE));
                String kosFacility = cursor.getString(cursor.getColumnIndex(COLUMN_KOS_FACILITY));
                String kosAddress= cursor.getString(cursor.getColumnIndex(COLUMN_KOS_ADDRESS));
                String kosLongitude = cursor.getString(cursor.getColumnIndex(COLUMN_KOS_LONGITUDE));
                String kosLatitude = cursor.getString(cursor.getColumnIndex(COLUMN_KOS_LATITUDE));
                String BookingDate = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DATE));
                bookedKosList.add(new BookedKos(BookingId, UserId,kosName,kosPrice,kosFacility,kosAddress,kosLongitude,kosLatitude,BookingDate));
            }
        }
        db.close();
        return bookedKosList;
    }

    public void Delete(String kos_name, String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"user_id = ? AND kos_name = ?",new String[]{user_id,kos_name});
        db.close();
    }
}
