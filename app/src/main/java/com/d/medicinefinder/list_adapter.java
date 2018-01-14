package com.d.medicinefinder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;



import java.util.ArrayList;

/**
 * Created by Ketan-PC on 10/8/2017.
 */

public class list_adapter extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> imaglist;


    public list_adapter(Activity context,ArrayList imaglist) {
        super(context, R.layout.image_item);

        this.context = context;
        this.imaglist= imaglist;


    }

    public int getCount() {

        return imaglist.size();
    }

    public String getItem(int position) {
        return imaglist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;

        if (rowView != null) {
            holder = (ViewHolder) rowView.getTag();
        }
        else {

            rowView=inflater.inflate(R.layout.image_item, null,true);
            holder = new ViewHolder(rowView);
            rowView.setTag(holder);
        }

        try {
            byte [] encodeByte= Base64.decode(imaglist.get(position),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);


            holder.img.setImageBitmap(bitmap);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }





        return rowView;
    }

    private class ViewHolder {
        private ImageView img;



        public ViewHolder(View v) {
            img = (ImageView) v.findViewById(R.id.item);


        }
    }


}
