package com.example.caton.androidmap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



import java.util.List;


public class TwoMapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private String locAddr1 = "";
    private double lat1;
    private double lng1;

    private String locAddr2 = "";
    private double lat2;
    private double lng2;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_two);

        // Get the message from the intent
        Intent intent = getIntent();
        String address1 = intent.getStringExtra("addr1");
        String address2 = intent.getStringExtra("addr2");


        Geocoder gc = new Geocoder(this);
        List<Address> addressList;

        try {
            addressList = gc.getFromLocationName(address1, 1);
            if (addressList != null) {
                lat1 = addressList.get(0).getLatitude();
                lng1 = addressList.get(0).getLongitude();
                locAddr1 += addressList.get(0).getAddressLine(0);
            }
            else {
                lat1 = 0.0;
                lng1 = 0.0;
                locAddr1 = "Address Not Found";
            }
        }
        catch(Exception e){
            lat1 = 0.0;
            lng1 = 0.0;
            locAddr1 = "Address Not Found";
        }

        try {
            addressList = gc.getFromLocationName(address2, 1);
            if (addressList != null) {
                lat2 = addressList.get(0).getLatitude();
                lng2 = addressList.get(0).getLongitude();
                locAddr2 += addressList.get(0).getAddressLine(0);
            }
            else {
                lat2 = 0.0;
                lng2 = 0.0;
                locAddr2 = "Address Not Found";
            }
        }
        catch(Exception e){
            lat2 = 0.0;
            lng2 = 0.0;
            locAddr2 = "Address Not Found";
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        if(locAddr2.equals("Address Not Found") || locAddr1.equals("Address Not Found")) {
            LatLng latlng = new LatLng(0,0);
            map.addMarker(new MarkerOptions().position(latlng).title("Address Error!"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 11.0f));
        }
        else {
            LatLng latLng = new LatLng(lat1, lng1);
            map.addMarker(new MarkerOptions().position(latLng).title(locAddr1));
            latLng = new LatLng(lat2, lng2);
            map.addMarker(new MarkerOptions().position(latLng).title(locAddr2));

            latLng = midPoint(lat1, lng1, lat2, lng2);
            double distance = distance(lat1, lat2, lng1, lng2, 1, 1);

            TextView textView = findViewById(R.id.map_two_text);
            textView.setText(Double.toString(distance/1000.0) + "km");

            map.addMarker(new MarkerOptions().position(latLng).title("Midpoint"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5.0f));
        }
    }

    //from https://stackoverflow.com/a/4656937
    //finds midpoint between two locations using latlng calculations
    public static LatLng midPoint(double lat1,double lon1,double lat2,double lon2){

        double dLon = Math.toRadians(lon2 - lon1);

        //convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);

        double Bx = Math.cos(lat2) * Math.cos(dLon);
        double By = Math.cos(lat2) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
        double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

        //return LatLng object
        return new LatLng(Math.toDegrees(lat3), Math.toDegrees(lon3));
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
