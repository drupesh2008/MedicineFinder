package com.d.medicinefinder;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MedicineSearch extends AppCompatActivity implements OnMapReadyCallback{
    MaterialSearchView searchView;
    ListView listView;
    ArrayAdapter adapter;
    MapFragment mMapFragment;

    ArrayList<String> contact = new ArrayList<>();
    ArrayList<LatLng> hospital = new ArrayList<>();
    ArrayList<String> hospitalname = new ArrayList<>();
    ArrayList<String> medicineshop = new ArrayList<>();
    ArrayList<String> medicinename= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_search);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);

        searchView = (MaterialSearchView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.list);

        mMapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        searchName();



        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                searchDb(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


    }

     public void alertdialog1 (View view) {
         alertdialog();

     }
    //Alert Dialog
    void alertdialog (){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View customView = getLayoutInflater().inflate(R.layout.activity_multiple_medicine, null);
        ArrayAdapter adapter;


        final AutoCompleteTextView medname = (AutoCompleteTextView) customView.findViewById(R.id.editText);
        AutoCompleteTextView medname2 = (AutoCompleteTextView) customView.findViewById(R.id.editText2);
        AutoCompleteTextView medname3 = (AutoCompleteTextView) customView.findViewById(R.id.editText3);

        Button search = (Button) customView.findViewById(R.id.button);

        mBuilder.setView(customView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


        adapter  = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, medicinename);
        //Add medicines arraylist in place of  array list in the line above

        medname.setAdapter(adapter);
        medname2.setAdapter(adapter);
        medname3.setAdapter(adapter);

        medname.setThreshold(1);
        medname2.setThreshold(1);
        medname3.setThreshold(1);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call search db here
                searchDb(medname.getText().toString());
            }
        });

    }

    void  searchDb(final String medname){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait...");
        dialog.show();
        String url ="http://medicinefinder.000webhostapp.com/upload/Marker.php";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("", "Login Response: " + response.toString());


                try {
                    JSONArray jObj = new JSONArray(response);
                    hospital.clear();
                    hospitalname.clear();
                    contact.clear();

                    for(int i=0;i<jObj.length();i++)
                    {
                        String str = jObj.getJSONObject(i).getString("Location");
                        String name = jObj.getJSONObject(i).getString("Name");

                        String phone = jObj.getJSONObject(i).getString("Phone");


                        double Lat = Double.parseDouble(str.substring(str.indexOf("(")+1,str.indexOf(",")));
                        double Lng = Double.parseDouble(str.substring(str.indexOf(",")+1,str.indexOf(")")));
                        contact.add(phone);
                        hospital.add(new LatLng(Lat,Lng));
                        hospitalname.add(name);
                    }

                    dialog.dismiss();



                 Toast.makeText(getApplicationContext(),"Available",Toast.LENGTH_LONG).show();

                   mMapFragment.getMapAsync(MedicineSearch.this);

                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

                Toast.makeText(getApplicationContext(),"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();


                params.put("medicine", medname);



                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "");
    }

    void  searchName(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait...");
        final ArrayList<String> abc = new ArrayList<>();
        dialog.show();
        String url ="http://medicinefinder.000webhostapp.com/upload/MedicineNames.php";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("", "Login Response: " + response.toString());



                try {
                    JSONArray jObj = new JSONArray(response);
                    abc.clear();

                    for(int i=0;i<jObj.length();i++)
                    {
                        abc.add(jObj.getString(i));
                    }

                    dialog.dismiss();
                    medicinename = new ArrayList<String>(new HashSet<String>(abc));

                    String[] stringArray = medicinename.toArray(new String[0]);

                    searchView.setSuggestions(stringArray);





                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

                Toast.makeText(getApplicationContext(),"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(strReq, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(hospital!=null) {
            for (int i=0;i<hospital.size();i++)
            {  googleMap.addMarker(new MarkerOptions().position(hospital.get(i)).title(hospitalname.get(i)+" Contact No:"+contact.get(i))).showInfoWindow();

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hospital.get(i), 16.0f));}



        }
    }


    }

