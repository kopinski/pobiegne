package pl.pobiegne.mobile.listener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.joda.time.DateTime;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.activity.MainActivity;
import pl.pobiegne.mobile.util.StorageUtil;
import pl.pobiegne.mobile.xml.converter.HashMapConverter;
import pl.pobiegne.mobile.xml.converter.JodaTimeConverter;
import pl.pobiegne.mobile.xml.data.GPX;
import pl.pobiegne.mobile.xml.data.Track;
import pl.pobiegne.mobile.xml.data.TrackSegment;
import pl.pobiegne.mobile.xml.data.WayPoint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.Trace;
import com.googlecode.androidannotations.annotations.ViewById;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.mapper.Mapper;


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
    protected TextView gpsState;
    
    @ViewById(R.id.distance)
    protected TextView distance;
    
    @ViewById(R.id.altitude)
    protected TextView altitude;
    
    private StringBuffer stringBuffer;
    
    private GPX gpx = new GPX();
    
    private WayPoint wayPoint;
    
    private XStream stream;
    
    private TrackSegment trackSegment;
    
    @Trace
    @Click(R.id.start)
    protected void click() {
        StorageUtil storage = new StorageUtil(context);
        storage.saveStringToExternalStorage(stream.toXML(gpx), "pobiegne", new DateTime().toString("HHmm")+".xml", true);
    }
    
    public TrackerListener() {
        numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(3);
        numberFormat.setMinimumFractionDigits(3);
        totalDistance = 0.0;
        stringBuffer = new StringBuffer();
        stream = new XStream();
        stream.autodetectAnnotations(true);
        Mapper mapper = stream.getMapper();
        ReflectionProvider reflectionProvider = stream.getReflectionProvider();
        stream.registerConverter(new JodaTimeConverter());
        stream.registerConverter(new HashMapConverter(mapper, reflectionProvider, GPX.class, gpx.getHashMap()));
    }
    
    @Override
    public void onLocationChanged(final Location location) {
        // final String methodName = "onLocationChanged";
        // Log.d(TAG, "START - " + methodName);
        gpsState.setText(context.getResources().getText(R.string.ok));
        stringBuffer.setLength(0);
        stringBuffer.append("lat: ");
        stringBuffer.append(location.getLatitude());
        stringBuffer.append(", lon: ");
        stringBuffer.append(location.getLongitude());
        stringBuffer.append(", fix: ");
        stringBuffer.append(location.getAccuracy());
        wayPoint =
                new WayPoint(location.getAltitude(), new DateTime(), location.getLongitude(), location.getLatitude());
        
        if (trackSegment == null) {
            trackSegment = new TrackSegment();
        }
        trackSegment.getTrackPoints().add(wayPoint);
        Log.d(TAG, stringBuffer.toString());
        if (firstDateTime == null) {
            firstDateTime = new DateTime(location.getTime());
        }
        if (oldLocation != null) {
            totalDistance += oldLocation.distanceTo(location) / 1000;
            distance.setText(numberFormat.format(totalDistance));
            altitude.setText(String.valueOf(location.getAltitude()));
        }
        oldLocation = location;
        // Log.d(TAG, "END - " + methodName);
    }
    
    @Override
    public void onProviderDisabled(final String provider) {
        final String methodName = "onProviderDisabled";
        Log.d(TAG, "START - " + methodName);
        gpsState.setText(R.string.not_available);
        Log.d(TAG, "END - " + methodName);
    }
    
    @Override
    public void onProviderEnabled(final String provider) {
        final String methodName = "onProviderEnabled";
        Log.d(TAG, "START - " + methodName);
        gpsState.setText(R.string.ok);
        Log.d(TAG, "END - " + methodName);
    }
    
    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {
        final String methodName = "onStatusChanged";
        Log.d(TAG, "START - " + methodName);
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                gpsState.setText(R.string.not_available);
                break;
            
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                gpsState.setText(R.string.not_available);
                break;
            
            case LocationProvider.AVAILABLE:
                if (trackSegment == null) {
                    trackSegment = new TrackSegment();
                }
                Track track = new Track();
                track.setName("Trasa z dnia" + new DateTime().toString("dd-MM-yyyy HH:mm"));
                track.getTrackSegments().add(trackSegment);
                ArrayList<Track> tracks = new ArrayList<Track>();
                tracks.add(track);
                gpx.setTracks(tracks);
                gpsState.setText(R.string.ok);
                break;
            
            default:
                gpsState.setText(R.string.startValue);
                break;
        }
        Log.d(TAG, "provider: " + provider + ", status: " + status);
        Log.d(TAG, "END - " + methodName);
    }
    
}