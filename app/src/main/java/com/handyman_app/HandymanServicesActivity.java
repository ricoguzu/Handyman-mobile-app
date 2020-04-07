package com.handyman_app;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.auth.FirebaseAuth;

public class HandymanServicesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Button mLogout,mSettings;
    private Boolean isLoggingOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handyman_services);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLogout   =   (Button) findViewById(R.id.logout);
        mSettings = (Button) findViewById(R.id.settings);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoggingOut = true;

                disconnectHandyman();

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HandymanServicesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HandymanServicesActivity.this, CustomerSettingsActivity.class);
                startActivity(intent);
                finish();
                return;
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void disconnectHandyman() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isLoggingOut){
            disconnectHandyman();

        }
    }
}
