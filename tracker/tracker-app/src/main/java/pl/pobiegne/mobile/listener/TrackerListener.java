package pl.pobiegne.mobile.listener;

import java.text.NumberFormat;
import java.util.Locale;

import org.joda.time.DateTime;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.activity.MainActivity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;


@EBean
public class TrackerListener implements LocationListener {
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    private Location oldLocation;
    
    private double totalDistance;
    
    private NumberFormat numberFormat;
    
    private DateTime firstDateTime;
    
    @RootContext
    MainActivity context;
    
    @ViewById(R.id.gpsState)
    protected TextView textView;
    
    @ViewById(R.id.distance)
    protected TextView distance;
    
    @ViewById(R.id.altitude)
    protected TextView altitude;
    
    @ViewById(R.id.currentTime)
    protected TextView time;
    
    
    public TrackerListener() {
        numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(3);
        numberFormat.setMinimumFractionDigits(3);
        totalDistance = 0.0;
    }
    
    @Override
    public void onLocationChanged(final Location location) {
        // final String methodName = "onLocationChanged";
        // Log.d(TAG, "START - " + methodName);
        // textView.setText("CHA");
        Log.d(TAG,
                "lat: " + location.getLatitude() + ", lon: " + location.getLongitude() + ", fix: "
                        + location.getAccuracy() + ", time: "
                        + new DateTime(location.getTime()).toLocalTime().toString());
        if (firstDateTime == null) {
            firstDateTime = new DateTime(location.getTime());
        }
        if (oldLocation != null) {
            totalDistance += oldLocation.distanceTo(location) / 1000;
            distance.setText(numberFormat.format(totalDistance));
            altitude.setText(String.valueOf(location.getAltitude()));
            time.setText(new DateTime().toString("HH:mm:ss", Locale.getDefault()));
        }
        oldLocation = location;
        // Log.d(TAG, "END - " + methodName);
    }
    
    @Override
    public void onProviderDisabled(final String provider) {
        final String methodName = "onProviderDisabled";
        Log.d(TAG, "START - " + methodName);
        textView.setText("DIS");
        Log.d(TAG, "END - " + methodName);
    }
    
    @Override
    public void onProviderEnabled(final String provider) {
        final String methodName = "onProviderEnabled";
        Log.d(TAG, "START - " + methodName);
        textView.setText("EN");
        Log.d(TAG, "END - " + methodName);
    }
    
    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {
        final String methodName = "onStatusChanged";
        Log.d(TAG, "START - " + methodName);
        Log.d(TAG, "provider: " + provider + ", status: " + status);
        Log.d(TAG, "END - " + methodName);
    }
    
}
