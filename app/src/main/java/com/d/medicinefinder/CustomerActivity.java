package com.d.medicinefinder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Button btn = (Button) findViewById(R.id.button4);

        btn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick (View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123"));
                startActivity(intent);
            }
        });
    }

    public void search(View view)
    {
        Intent i = new Intent(CustomerActivity.this,MedicineSearch.class);
        startActivity(i);
    }

    public void nearbyShops(View view)
    {
        Intent i = new Intent(CustomerActivity.this,MapsActivity.class);
        startActivity(i);
    }

    public void reminder(View view)
    {
        Intent i = new Intent(CustomerActivity.this,MainActivity.class);
        startActivity(i);
    }

    public void upload(View view)
    {
        Intent i = new Intent(CustomerActivity.this,Prescription.class);
        startActivity(i);
    }

}
