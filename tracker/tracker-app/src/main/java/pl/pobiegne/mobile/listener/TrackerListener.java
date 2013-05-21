package pl.pobiegne.mobile.listener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.joda.time.DateTime;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.util.StorageUtil;
import pl.pobiegne.mobile.xml.converter.HashMapConverter;
import pl.pobiegne.mobile.xml.converter.JodaTimeConverter;
import pl.pobiegne.mobile.xml.data.GPX;
import pl.pobiegne.mobile.xml.data.Track;
import pl.pobiegne.mobile.xml.data.TrackSegment;
import pl.pobiegne.mobile.xml.data.WayPoint;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.Trace;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.mapper.Mapper;


/**
 * Klasa lokalizatora zapisujaca do pliku przebyta przez uzytkownika trase do pliku gpx.
 */
@EBean
public class TrackerListener implements LocationListener {
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    private Location oldLocation;
    
    private double totalDistance;
    
    private NumberFormat distanceNumberFormat;
    
    private NumberFormat twoDigitNumberFormat;
    
    private long firstTime = 0;
    
    @RootContext
    protected Context context;
    
    @ViewById(R.id.gpsState)
    protected TextView gpsState;
    
    @ViewById(R.id.distance)
    protected TextView distance;
    
    @ViewById(R.id.altitude)
    protected TextView altitude;
    
    @ViewById(R.id.avaragePace)
    protected TextView avaragePace;
    
    @ViewById(R.id.currentPace)
    protected TextView currentPace;
    
    @ViewById(R.id.start)
    protected Button startButton;
    
    private StringBuffer stringBuffer;
    
    private GPX gpx = new GPX();
    
    private Track track = new Track();
    
    private WayPoint wayPoint;
    
    private XStream stream;
    
    private ArrayList<TrackSegment> trackSegmentList;
    
    public static int PAUSED = 0;
    
    public static int STOPED = -1;
    
    public static int STARTED = 1;
    
    private int status;
    
    
    public TrackerListener() {
        distanceNumberFormat = NumberFormat.getInstance(Locale.getDefault());
        distanceNumberFormat.setMaximumFractionDigits(3);
        distanceNumberFormat.setMinimumFractionDigits(3);
        twoDigitNumberFormat = NumberFormat.getInstance(Locale.getDefault());
        twoDigitNumberFormat.setMaximumFractionDigits(2);
        twoDigitNumberFormat.setMinimumFractionDigits(2);
        totalDistance = 0.0;
        stringBuffer = new StringBuffer();
        stream = new XStream();
        stream.autodetectAnnotations(true);
        Mapper mapper = stream.getMapper();
        ReflectionProvider reflectionProvider = stream.getReflectionProvider();
        stream.registerConverter(new JodaTimeConverter());
        stream.registerConverter(new HashMapConverter(mapper, reflectionProvider, GPX.class, gpx.getHashMap()));
        track.setName("Trasa z dnia " + new DateTime().toString("dd-MM-yyyy HH:mm"));
        trackSegmentList = new ArrayList<TrackSegment>();
        trackSegmentList.add(new TrackSegment());
        track.setTrackSegments(trackSegmentList);
        gpx.addTrack(track);
        status = STOPED;
    }
    
    @Override
    public void onLocationChanged(final Location location) {
        gpsState.setText(context.getResources().getText(R.string.ok));
        if (!startButton.isEnabled()) {
            startButton.setEnabled(true);
        }
        if (status == STARTED) {
            if (firstTime == 0) {
                firstTime = location.getTime();
            }
            if (oldLocation != null) {
                float currentDistance = oldLocation.distanceTo(location) / 1000;
                long timePeriod = location.getTime() - oldLocation.getTime();
                float actualPace = (currentDistance / timePeriod) * 3600000.0f;
                long totalTime = location.getTime() - firstTime;
                if (currentDistance / timePeriod < 0.01) {
                    totalDistance += currentDistance;
                    double meanPace = totalDistance / totalTime * 3600000.0;
                    addWaypoint(location);
                    logLocation(location, currentDistance);
                    currentPace.setText(twoDigitNumberFormat.format(actualPace));
                    avaragePace.setText(twoDigitNumberFormat.format(meanPace));
                }
                else {
                    Log.d(TAG, "zbyt duza szybkosc [km/h]=" + actualPace);
                }
            }
        }
        oldLocation = location;
        distance.setText(distanceNumberFormat.format(totalDistance));
        altitude.setText(twoDigitNumberFormat.format(location.getAltitude()));
    }
    
    private void addWaypoint(final Location location) {
        wayPoint =
                new WayPoint(location.getAltitude(), new DateTime(), location.getLongitude(), location.getLatitude());
        trackSegmentList.get(trackSegmentList.size() - 1).addTrackPoint(wayPoint);
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
                trackSegmentList.add(new TrackSegment());
                gpsState.setText(R.string.ok);
                break;
            
            default:
                gpsState.setText(R.string.startValue);
                break;
        }
        Log.d(TAG, "provider: " + provider + ", status: " + status);
        Log.d(TAG, "END - " + methodName);
    }
    
    @Trace
    public void startWorkout() {
        if (status == PAUSED) { // wznawiamy trening, czyli dodajemy trackSegment
            TrackSegment newTrkSeg = new TrackSegment();
            track.addTrackSegment(newTrkSeg);
        }
        setStatus(STARTED);
    }
    
    @Trace
    public void pauseWorkout() {
        setStatus(PAUSED);
        
    }
    
    @Trace
    @Background
    public void stopWorkout(ProgressDialog progressDialog) {
        setStatus(STOPED);
        StorageUtil storage = new StorageUtil(context);
        boolean savingResult = storage.saveStringToExternalStorage(stream.toXML(gpx), "pobiegne", new DateTime().toString("yyy-MM-dd_HH-mm")
                + ".gpx", true);
        endSaving(progressDialog, savingResult);
    }
    
    @UiThread
    protected void endSaving(ProgressDialog progressDialog, boolean savingResult) {
        progressDialog.dismiss();
        Toast.makeText(context, "zapisano: "  + savingResult, Toast.LENGTH_SHORT).show();
    }

    private void logLocation(final Location location, float currentDistance) {
        stringBuffer.setLength(0);
        stringBuffer.append("lat: ");
        stringBuffer.append(location.getLatitude());
        stringBuffer.append(", lon: ");
        stringBuffer.append(location.getLongitude());
        stringBuffer.append(", fix: ");
        stringBuffer.append(location.getAccuracy());
        stringBuffer.append(", dis: ");
        stringBuffer.append(currentDistance);
        stringBuffer.append(", prov: ");
        stringBuffer.append(location.getProvider());
        Log.d(TAG, stringBuffer.toString());
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
}