package pl.pobiegne.mobile.activity;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.listener.TrackerListener;
import android.app.Activity;
import android.location.LocationManager;
import android.util.Log;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;


@EActivity(R.layout.main_layout)
public class MainActivity extends Activity {
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    @ViewById(R.id.gpsState)
    protected TextView textView;
    
    @ViewById(R.id.workOutTime)
    protected TextView clock;
    
    @SystemService
    LocationManager locationManager;
    
    @Bean
    TrackerListener trackerListener;
    
    
    @AfterViews
    void after() {
        final String methodName = "after";
        Log.d(TAG, "START - " + methodName);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, trackerListener);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, trackerListener);
        updateClock();
        Log.d(TAG, "END - " + methodName);
    }
    
    @UiThread(delay = 1000)
    protected void updateClock() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("hh:mm");
        clock.setText(formatter.withLocale(Locale.getDefault()).print(DateTime.now()));
    }
    
    @Override
    protected void onPause() {
        locationManager.removeUpdates(trackerListener);
        super.onPause();
    }
}