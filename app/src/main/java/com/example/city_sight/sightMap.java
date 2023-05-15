package com.example.city_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class sightMap extends AppCompatActivity {
    MapView mapview;
    TextView textView;
    TextView textView2;
//    TextView textView3;
//    TextView textView4;
//    TextView textView5;

    Sight sight = new Sight();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("3e9ed211-3558-476a-ab52-9b29735e3a9e");
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

        mapview.getMap().getMapObjects().addPlacemark(sight.getCoordinates());

        textView = (TextView) findViewById(R.id.mapTitle);
        textView.setText((sight.getTitle()));
        textView2 = (TextView) findViewById(R.id.discTitle);
        textView2.setText((sight.getFullDisc()));
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }
}