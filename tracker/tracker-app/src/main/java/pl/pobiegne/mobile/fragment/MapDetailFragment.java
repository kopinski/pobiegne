package pl.pobiegne.mobile.fragment;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.googlecode.androidannotations.annotations.EFragment;


@EFragment(R.layout.map_detail_layout)
public class MapDetailFragment extends SupportMapFragment {
    
    private ArrayList<LatLng> path = new ArrayList<LatLng>();
    
    private GoogleMap map;
    
    private LatLngBounds.Builder boundsBuilder;
    
    public static MapDetailFragment newInstance(ArrayList<LatLng> path) {
        MapDetailFragment fragment = new MapDetailFragment();
        fragment.path = path;
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        initMap();
        return v;
    }
    
    private void initMap() {
        map = getMap();
        UiSettings settings = map.getUiSettings();
        settings.setMyLocationButtonEnabled(false);
        PolylineOptions pathOption = new PolylineOptions();
        pathOption.color(Color.BLUE).width(10);
        boundsBuilder = new LatLngBounds.Builder();
        for (LatLng data : path) {
            pathOption.add(data);
            boundsBuilder.include(data);
        }
        map.addPolyline(pathOption);
        map.setOnCameraChangeListener(new OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {
                if (path.size() > 1)
                    map.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 50));
                map.setOnCameraChangeListener(null);
            }
        });
    }
}