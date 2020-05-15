package com.example.bluejackkoslab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bluejackkoslab.AppController;
import com.example.bluejackkoslab.R;
import com.example.bluejackkoslab.model.Item;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Item> items;
    ImageLoader imageLoader = AppController.getInstance().getmImageLoader();

    public CustomAdapter(Context context, int resource, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( inflater == null) {
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.kos_info, null);
        }
        if(imageLoader == null) {
            imageLoader = AppController.getInstance().getmImageLoader();
        }
            NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.kos_img);
            TextView name = (TextView) convertView.findViewById(R.id.kos_name);
            TextView price = (TextView) convertView.findViewById(R.id.kos_price);
            TextView facilities = (TextView) convertView.findViewById(R.id.kos_facility);

            Item item = items.get(position);
            imageView.setImageUrl(item.getImage(), imageLoader);
            name.setText(item.getName());
            price.setText("Price : Rp " + (item.getPrice()));
            facilities.setText("Facilities : " + item.getFacilities());

            return convertView;
        }
    }
