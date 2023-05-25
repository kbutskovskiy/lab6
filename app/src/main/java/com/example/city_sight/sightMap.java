package com.example.city_sight;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.FilteringMode;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.ui_view.ViewProvider;

import java.io.File;

public class sightMap extends AppCompatActivity {
    MapView mapview;
    private static final int REQUEST_CODE_LOCATION = 1;

    TextView textView;
    TextView textView2;

    User user = new User();
    Button showLocation;

    Sight sight = new Sight();
    private LocationManager locationManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sight_map);
        Bundle arguments = getIntent().getExtras();
        sight.setTitle(arguments.getString("title"));
        sight.setFullDisc(arguments.getString("fullDisc"));
        sight.setWorkHours(arguments.getString("workHours"));
        sight.setCoordinates(new Point((Double) arguments.get("latitude"), (Double) arguments.get("longitude")));
        mapview = (MapView)findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(sight.getCoordinates(), 16.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        ImageView sightImageView = findViewById(R.id.sightPhoto);
        Picasso.get().load(sight.getPhoto()).into(sightImageView);


        /*final boolean[] focusOnUserLocation = {true};

        mapview.getMap().addCameraListener(new CameraListener() {
            @Override
            public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateReason cameraUpdateReason, boolean b) {
                focusOnUserLocation[0] = false;
            }
        });*/


        mapview.getMap().getMapObjects().addPlacemark(sight.getCoordinates());

        textView = (TextView) findViewById(R.id.mapTitle);
        textView.setText((sight.getTitle()));
        textView2 = (TextView) findViewById(R.id.discTitle);
        textView2.setText((sight.getFullDisc()));

        showLocation = (Button) findViewById(R.id.showLocation);
        locationManager = MapKitFactory.getInstance().createLocationManager();

        // Check if the app has the ACCESS_FINE_LOCATION permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If the permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }


        showLocation.setOnClickListener(v -> {
            if (locationManager != null) {
                // Request location updates
                locationManager.subscribeForLocationUpdates(0,0, 0, true, FilteringMode.ON, new LocationListener() {
                    @Override
                    public void onLocationUpdated(@NonNull Location location) {
                        // Here you can get the user's location and move the map to it
                        Point userLocation = new Point(location.getPosition().getLatitude(), location.getPosition().getLongitude());
                        //System.out.println(userLocation.getLatitude() + "   " + userLocation.getLongitude());
                        mapview.getMap().move(
                                new CameraPosition(new Point(location.getPosition().getLatitude(), location.getPosition().getLongitude()), 14.0f, 0.0f, 0.0f),
                                new Animation(Animation.Type.SMOOTH, 0),
                                null);

                        // Add the user location marker to the map
                        mapview.getMap().getMapObjects().addPlacemark(userLocation);

                    }

                    @Override
                    public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
                        // Handle location status updates
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }



    /*@Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, sightList.class);
        intent.putExtra("name", user.getName());
        intent.putExtra("surname", user.getSurname());
        intent.putExtra("email", user.getEmail());
        startActivity(intent);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }
}