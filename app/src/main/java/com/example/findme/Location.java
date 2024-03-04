package com.example.findme;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Location extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private SearchView searchBar;
    private Button zoomInButton, zoomOutButton;
    private Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        searchBar = findViewById(R.id.searchBar);
        zoomInButton = findViewById(R.id.zoomInButton);
        zoomOutButton = findViewById(R.id.zoomOutButton);
        Button buttonOk = findViewById(R.id.buttonOk);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(Location.this);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchBar.getQuery().toString();
                List<Address> adressList = null;
                if(location!=null){
                    Geocoder geocoder = new Geocoder(Location.this, Locale.getDefault());
                    try{
                        adressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if(adressList.size() > 0){
                        Address adress = adressList.get(0);
                        LatLng latLng = new LatLng(adress.getLatitude(),adress.getLongitude());
                        if (marker!= null) {
                            marker.remove();
                        }
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(location);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        marker = mMap.addMarker(markerOptions);
                    }



                }else{
                    Toast.makeText(Location.this,"Locatia nu a fost gasita", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(Location.this);

        zoomInButton.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.zoomIn()));

        zoomOutButton.setOnClickListener(v -> mMap.animateCamera(CameraUpdateFactory.zoomOut()));
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng currentLocation = mMap.getCameraPosition().target;
                if (!currentLocation.equals(new LatLng(47.6333 , 26.25))) {

                    LocationManager.saveLocation(currentLocation.latitude, currentLocation.longitude);
                    finish();
                } else {

                    Toast.makeText(Location.this, "Locația selectată este aceeași cu locația inițială.", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
    @Override
    public void onMapReady( GoogleMap googleMap) {
        this.mMap = googleMap;
        LatLng location = new LatLng(47.6333 , 26.25);
        mMap.addMarker(new MarkerOptions().position(location).title("Suceava"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12));

        mMap.setOnMapClickListener(latLng -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        });
    }
}
