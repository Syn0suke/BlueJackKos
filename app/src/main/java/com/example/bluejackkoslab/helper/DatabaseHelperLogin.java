package com.example.bluejackkoslab.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelperLogin extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Login.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "user_data";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "user_name";
    private static final String COLUMN_PHONE = "user_phone";
    private static final String COLUMN_PASS = "user_pass";
    private static final String COLUMN_GENDER= "user_gender";
    private static final String COLUMN_DOB= "user_dob";

    public DatabaseHelperLogin(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " TEXT PRIMARY KEY NOT NULL, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_PHONE + " TEXT NOT NULL, " +
                        COLUMN_PASS + " TEXT NOT NULL, " +
                        COLUMN_GENDER + " TEXT NOT NULL, " +
                        COLUMN_DOB + " TEXT NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(String id, String name, String phone, String pass,String gender,String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cval = new ContentValues();

        cval.put(COLUMN_ID,id);
        cval.put(COLUMN_NAME,name);
        cval.put(COLUMN_PHONE,phone);
        cval.put(COLUMN_PASS,pass);
        cval.put(COLUMN_GENDER,gender);
        cval.put(COLUMN_DOB,dob);
        long result = db.insert(TABLE_NAME, null, cval);
        if (result == -1) {
            Toast.makeText(context, "Failed to add data", Toast.LENGTH_LONG).show();
            db.close();
        } else
        {
            Toast.makeText(context, "Add data successful", Toast.LENGTH_LONG).show();
            db.close();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public int Count()
    {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(sql,null);

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();

        return count;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME+" where user_name=?" , new String[]{username});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    public boolean checkUsernameAndPass(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_NAME+" where user_name=? and user_pass=?" ,new String[]{username,password});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else{
            cursor.close();
            return false;
        }
    }

    public String getId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select _id from "+TABLE_NAME+" where user_name=? and user_pass=?" ,new String[]{username,password});
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            return cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        } else {
            cursor.close();
            return null;
        }
    }
}
