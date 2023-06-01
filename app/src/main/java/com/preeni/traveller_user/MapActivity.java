package com.preeni.traveller_user;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    SupportMapFragment mapView;
    EditText et_search;
    ImageView iv_menu;
    Location mLastLocation;
    Marker mCurrentLocationMarker;
    FusedLocationProviderClient client;
    private String lat, lan = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        et_search = findViewById(R.id.et_search);
        iv_menu = findViewById(R.id.iv_menu);

        mapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapView.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(this);

        getCurrentLocation();

        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  String uri = String.format(Locale.ENGLISH, "geo:lat,%f", lat, lan);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);*/
            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {

                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                lat = String.valueOf(location.getLatitude());
                                lan = String.valueOf(location.getLongitude());
                                // LatLng latLng = new LatLng(34.06596486288056, -118.35927594092865);
                                MarkerOptions options = new MarkerOptions().position(latLng)
                                        .title("You are here...");
                                options.icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.ic_baseline_boy_24));
                                googleMap.addMarker(options);
                            }
                        });
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    Log.d("Map Permission", "Request permission to show Map...");
                    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                    requestPermissions(permissions, 100);

                }
            }

            getCurrentLocation();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng latLng = new LatLng(7.6257674847447205, 80.7427385173159);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));

        setLocationsOnMap("Galle", 6.0299377592360495, 80.21688265686285, gMap);
        setLocationsOnMap("Kandy", 7.345777808906727, 80.645926690294, gMap);
        setLocationsOnMap("Ella", 6.866885342206011, 81.04666042141311, gMap);
        setLocationsOnMap("Nuwara-eliya", 6.956759030637891, 80.79786367474594, gMap);
        setLocationsOnMap("Trinco", 8.627084275885403, 81.23627098077937, gMap);
        setLocationsOnMap("Jaffna", 9.67171063910848, 79.91207399221571, gMap);
        setLocationsOnMap("Udawalawa", 6.418515998381718, 80.82342034855616, gMap);
        setLocationsOnMap("Yala", 6.330605537207762, 81.38712015119938, gMap);
        setLocationsOnMap("Dambulla", 7.874632831919775, 80.64784830253681, gMap);
        setLocationsOnMap("Arugam Bay", 6.8379213883283025, 81.82586998935932, gMap);
        setLocationsOnMap("Anuradhapura", 8.318427298183579, 80.40976247822935, gMap);

    }

    private void setLocationsOnMap(String location_name, double lat, double lang, @NonNull GoogleMap gMap) {
        LatLng latLng = new LatLng(lat, lang);
        MarkerOptions options = new MarkerOptions().position(latLng)
                .title(location_name);
        options.icon(bitmapDescriptorFromVector(this, R.drawable.ic_baseline_push_pin_24));
        gMap.addMarker(options);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}