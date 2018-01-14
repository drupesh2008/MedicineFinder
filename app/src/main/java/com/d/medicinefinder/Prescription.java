package com.d.medicinefinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Prescription extends AppCompatActivity {
    SharedPreferences preference;
    ArrayList<String>  imageList = new ArrayList<>();

     ListView listview ;

    list_adapter adaptor;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                ImageView imageView = (ImageView) findViewById(R.id.imageView);

                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte [] b=baos.toByteArray();
                String temp= Base64.encodeToString(b, Base64.DEFAULT);


                imageList.add(temp);


                    Set<String> hashset = new HashSet<String>(imageList);
                SharedPreferences.Editor editor = preference.edit();

                editor.putStringSet("ImgList",hashset);
                editor.apply();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        preference = getSharedPreferences("mypref", Context.MODE_PRIVATE);


      listview = (ListView) findViewById(R.id.list1);
        imageList=new ArrayList<>(preference.getStringSet("ImgList",new HashSet<String>()));

        if(imageList.size()>0) {
            adaptor = new list_adapter(this, imageList);
            listview.setAdapter(adaptor);
            //Done :D
        }



    }

    public void upload(View view){
        Intent i =new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Image"),1);
    }


}
