package com.example.caton.androidmap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



import java.util.List;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private String locAddr = "";
    private double lat;
    private double lng;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        // Get the message from the intent
        Intent intent = getIntent();
        String address = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Geocoder gc = new Geocoder(this);
        List<Address> addressList;

        try {
            addressList = gc.getFromLocationName(address, 1);
            if (addressList != null) {
                lat = addressList.get(0).getLatitude();
                lng = addressList.get(0).getLongitude();
                locAddr += addressList.get(0).getAddressLine(0);
            }
            else {
                lat = 0.0;
                lng = 0.0;
                locAddr = "Address Not Found";
            }
        }
        catch(Exception e){
            lat = 0.0;
            lng = 0.0;
            locAddr = "Address Not Found";
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions().position(latLng).title(locAddr));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.0f));
    }
}
