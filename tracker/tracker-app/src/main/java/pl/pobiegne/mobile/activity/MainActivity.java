package pl.pobiegne.mobile.activity;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.adapter.BaseSpinnerAdapter;
import pl.pobiegne.mobile.adapter.IconSpinnerAdapter;
import pl.pobiegne.mobile.listener.TrackerListener;
import pl.pobiegne.mobile.xml.converter.HashMapConverter;
import pl.pobiegne.mobile.xml.converter.JodaTimeConverter;
import pl.pobiegne.mobile.xml.data.GPX;
import pl.pobiegne.mobile.xml.data.Track;
import pl.pobiegne.mobile.xml.data.TrackSegment;
import pl.pobiegne.mobile.xml.data.WayPoint;
import android.app.Activity;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.Trace;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.mapper.Mapper;


@EActivity(R.layout.main_layout)
public class MainActivity extends Activity {
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    @ViewById(R.id.gpsState)
    protected TextView gpsState;
    
    @ViewById(R.id.currentTime)
    protected TextView clock;
    
    @ViewById(R.id.kind)
    protected Spinner kindSpinner;
    
    @ViewById(R.id.activity)
    protected Spinner activitySpinner;
    
    @SystemService
    protected LocationManager locationManager;
    
    @Bean
    protected TrackerListener trackerListener;
    
    @Bean
    protected BaseSpinnerAdapter adapter;
    
    @Bean
    protected IconSpinnerAdapter iconAdapter;
    
    private DateTimeFormatter formatter;
    
    
    @AfterViews
    void after() {
        final String methodName = "after";
        Log.d(TAG, "START - " + methodName);
        updateRequest();
        formatter = DateTimeFormat.forPattern("HH:mm:ss");
        clock.setText(formatter.print(DateTime.now()));
        updateClock();
        
        activitySpinner.setAdapter(iconAdapter);
        kindSpinner.setAdapter(adapter);
        // XStream stream = new XStream();
        // stream.autodetectAnnotations(true);
        // Mapper mapper = stream.getMapper();
        // ReflectionProvider reflectionProvider = stream.getReflectionProvider();
        // WayPoint wayPoint = new WayPoint(new DateTime(), 12, 1.2, 23.2);
        // TrackSegment trackSegment = new TrackSegment();
        // trackSegment.getTrackPoints().add(wayPoint);trackSegment.getTrackPoints().add(wayPoint);trackSegment.getTrackPoints().add(wayPoint);trackSegment.getTrackPoints().add(wayPoint);trackSegment.getTrackPoints().add(wayPoint);trackSegment.getTrackPoints().add(wayPoint);
        // Track track = new Track();
        // track.getTrackSegments().add(trackSegment);
        // ArrayList<Track> tracks = new ArrayList<Track>();
        // tracks.add(track);
        // GPX gpx = new GPX();
        // gpx.setTracks(tracks);
        // stream.registerConverter(new JodaTimeConverter());
        // stream.registerConverter(new HashMapConverter(mapper, reflectionProvider, GPX.class, gpx.getHashMap()));
        // Log.e(TAG, "XML - " + stream.toXML(gpx));
        Log.d(TAG, "END - " + methodName);
    }
    
    private void updateRequest() {
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, trackerListener);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, trackerListener);
    }
    
    @UiThread(delay = 1000)
    protected void updateClock() {
        clock.setText(formatter.print(DateTime.now()));
        updateClock();
    }
    
    @Override
    protected void onPause() {
        locationManager.removeUpdates(trackerListener);
        super.onPause();
    }
    
    @Override
    @Trace
    protected void onResume() {
        updateRequest();
        super.onPause();
    }
}