package pl.pobiegne.mobile.fragment;

import java.util.Locale;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.db.Route;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {
    
    protected TextView kind;
    
    protected TextView activity;
    
    protected TextView distance;
    
    protected TextView averagePace;
    
    protected TextView averageSpeed;
    
    protected TextView workoutTime;
    
    private Route route;
    
    
    public static DetailFragment newInstance(Route route) {
        DetailFragment fragment = new DetailFragment();
        fragment.route = route;
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_layout, container, false);
        kind = (TextView) v.findViewById(R.id.kind);
        activity = (TextView) v.findViewById(R.id.activity);
        distance = (TextView) v.findViewById(R.id.distance);
        averageSpeed = (TextView) v.findViewById(R.id.averageSpeed);
        workoutTime = (TextView) v.findViewById(R.id.workOutTime);
        DateTimeFormatter timeFormat = DateTimeFormat.forPattern("m:ss").withZoneUTC();
        distance.setText(String.format(Locale.getDefault(), "%.3f", route.getDistance() / 1000));
        workoutTime.setText(timeFormat.print(route.getWorkoutTime()));
        long speed = Math.round(route.getDistance() / route.getWorkoutTime() * 3600);
        if (speed != 0) {
            averagePace = (TextView) v.findViewById(R.id.averagePace);
            averagePace.setText(timeFormat.print(Math.round((route.getWorkoutTime() / 3600000.f)
                    / (route.getDistance() / 1000))));
        }
        averageSpeed.setText(speed + "");
        String[] activtiyArray = getResources().getStringArray(R.array.activtiy);
        String[] kindArray = getResources().getStringArray(R.array.kind);
        
        TypedArray activitiesImg = getResources().obtainTypedArray(R.array.activtiy_imgs);
        activity.setText(activtiyArray[route.getActivity().ordinal()]);
        activity.setCompoundDrawablesWithIntrinsicBounds(
                activitiesImg.getResourceId(route.getActivity().ordinal(), -1), 0, 0, 0);
        kind.setText(kindArray[route.getType().ordinal()]);
        return v;
    }
}