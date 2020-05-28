package com.example.bluejackkoslab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bluejackkoslab.adapter.CustomAdapter;
import com.example.bluejackkoslab.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class KosList extends AppCompatActivity {
    private static final String url = "https://raw.githubusercontent.com/dnzrx/SLC-REPO/master/MOBI6006/E202-MOBI6006-KR01-00.json";
    private ProgressDialog dialog;
    private List<Item> array = new ArrayList<Item>();
    private ListView listView;
    private CustomAdapter adapter;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos_list);

        listView = (ListView) findViewById(R.id.lv_list);
        loginPreferences = getSharedPreferences("logindata", Context.MODE_PRIVATE);
        loginEditor = loginPreferences.edit();
        adapter = new CustomAdapter(this,0,array);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(KosList.this, KosDetail.class);
                intent.putExtra("kos_name", array.get(position).getName());
                intent.putExtra("kos_price",array.get(position).getPrice());
                intent.putExtra("kos_facility",array.get(position).getFacilities());
                intent.putExtra("kos_address",array.get(position).getAddress());
                intent.putExtra("kos_longitude",array.get(position).getLNG());
                intent.putExtra("kos_latitude",array.get(position).getLAT());
                intent.putExtra("kos_image",array.get(position).getImage());
                startActivity(intent);
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                for(int i=0; i<response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Item item = new Item();
                        item.setId(obj.getInt("id"));
                        item.setName(obj.getString("name"));
                        item.setImage(obj.getString("image"));
                        item.setFacilities(obj.getString("facilities"));
                        item.setPrice(obj.getInt("price"));
                        item.setAddress(obj.getString("address"));
                        item.setLAT(obj.getDouble("LAT"));
                        item.setLNG(obj.getDouble("LNG"));
                        array.add(item);
                    } catch(JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(JsonArrayRequest);
    }
    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.book:
                intent = new Intent(KosList.this, Booking.class);
                startActivity(intent);
                break;

            case R.id.logout:
                intent = new Intent(KosList.this, MainActivity.class);
                loginEditor.clear();
                loginEditor.commit();
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
