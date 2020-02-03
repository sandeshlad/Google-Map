package com.example.googlemap;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button go,type,zoomin,zoomout;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        go=findViewById(R.id.go);
        type=findViewById(R.id.type);
        zoomin=findViewById(R.id.zoomin);
        zoomout=findViewById(R.id.zoomout);

        search=findViewById(R.id.search);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=search.getText().toString();

                if(s.isEmpty()){
                    search.setError("Please Enter Something");
                    search.requestFocus();
                    return;
                }
                List<Address> list=null;

                Geocoder geocoder=new Geocoder(MapsActivity.this);
                try {
                    list=geocoder.getFromLocationName(s,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address=list.get(0);
                LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in your place"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));

            }
        });

        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
                else
                {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }

            }
        });

        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());

            }
        });

        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pune = new LatLng(18.510937, 73.817590);
        mMap.addMarker(new MarkerOptions().position(pune).title("Marker in Felix"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pune));
    }
}
