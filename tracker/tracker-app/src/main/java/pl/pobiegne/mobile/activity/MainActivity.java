package pl.pobiegne.mobile.activity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.adapter.BaseSpinnerAdapter;
import pl.pobiegne.mobile.adapter.IconSpinnerAdapter;
import pl.pobiegne.mobile.adapter.MenuSpinnerAdapter;
import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.dao.IRoute;
import pl.pobiegne.mobile.dao.RouteManager;
import pl.pobiegne.mobile.navigation.Navigate;
import pl.pobiegne.mobile.service.TrackerService;
import pl.pobiegne.mobile.util.StorageUtil;
import pl.pobiegne.mobile.xml.converter.HashMapConverter;
import pl.pobiegne.mobile.xml.converter.JodaTimeConverter;
import pl.pobiegne.mobile.xml.data.GPX;
import pl.pobiegne.mobile.xml.data.Track;
import pl.pobiegne.mobile.xml.data.TrackSegment;
import pl.pobiegne.mobile.xml.data.WayPoint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.Trace;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.DrawableRes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.mapper.Mapper;


/**
 * @author Krzysztof Kopiński
 */
@EActivity(R.layout.main_layout)
// @OptionsMenu(R.menu.main_menu)
public class MainActivity extends Activity {
    
    public static final long MIN_TIME_UPDATE = 0;
    
    public static final long MIN_DITANCE_UPDATE = 0;
    
    public static int PAUSED = 0;
    
    public static int STOPED = -1;
    
    public static int STARTED = 1;
    
    @ViewById(R.id.gpsState)
    protected TextView gpsState;
    
    @ViewById(R.id.currentTime)
    protected TextView clock;
    
    @ViewById(R.id.kind)
    protected Spinner kindSpinner;
    
    @ViewById(R.id.activity)
    protected Spinner activitySpinner;
    
    @ViewById(R.id.pauseWorkout)
    protected Button pause;
    
    @Bean
    protected BaseSpinnerAdapter adapter;
    
    @Bean
    protected IconSpinnerAdapter iconAdapter;
    
    @Bean
    protected MenuSpinnerAdapter menuAdapter;
    
    @Bean(RouteManager.class)
    protected IRoute routeManager;
    
    @ViewById(R.id.startLayout)
    protected RelativeLayout startLayout;
    
    @ViewById(R.id.stopLayout)
    protected RelativeLayout stopLayout;
    
    @ViewById(R.id.statisticFrameLayout)
    protected FrameLayout statisticLayout;
    
    @DrawableRes(R.drawable.resume)
    protected Drawable pauseDrawable;
    
    @ViewById(R.id.altitude)
    protected TextView altitude;
    
    @ViewById(R.id.distance)
    protected TextView distance;
    
    @ViewById(R.id.avaragePace)
    protected TextView avaragePace;
    
    @ViewById(R.id.currentPace)
    protected TextView currentPace;
    
    @ViewById(R.id.workOutTime)
    protected TextView workoutTime;
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    /**
     * Formater godziny.
     */
    private DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm:ss");
    
    /**
     * Formater z 3 cyframi po przecinku.
     */
    private NumberFormat threeDigitFormat;
    
    /**
     * Formater z 2 cyframi po przecinku.
     */
    private NumberFormat twoDigitFormat;
    
    /**
     * Czy polaczono z serwisem.
     */
    private boolean isBound;
    
    // private boolean gpsProviderEnabled;
    
    // private boolean isProviderAvailable;
    
    /**
     * Czy zlecono uaktualnianie pozycji?
     */
    private boolean requested;
    
    private GPX gpx = new GPX();
    
    private Track track = new Track();
    
    private WayPoint wayPoint;
    
    private XStream stream;
    
    private ArrayList<TrackSegment> trackSegmentList;
    
    private Location oldLocation;
    
    private double totalDistance;
    
    private long firstTime = 0;
    
    private int status;
    
    private Messenger service;
    
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    
    private Duration workouDuration;
    
    /**
     * Polaczenie do serwisu lokalizujacego.
     */
    private ServiceConnection connection = new ServiceConnection() {
        
        public void onServiceConnected(ComponentName className, IBinder serviceBinder) {
            service = new Messenger(serviceBinder);
            try {
                Message msg = Message.obtain(null, TrackerService.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                service.send(msg);
            }
            catch (RemoteException e) {
            }
        }
        
        public void onServiceDisconnected(ComponentName className) {
            service = null;
        }
    };
    
    @Extra
    int selectedValue;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        threeDigitFormat = NumberFormat.getInstance(Locale.getDefault());
        threeDigitFormat.setMaximumFractionDigits(3);
        threeDigitFormat.setMinimumFractionDigits(3);
        twoDigitFormat = NumberFormat.getInstance(Locale.getDefault());
        twoDigitFormat.setMaximumFractionDigits(2);
        twoDigitFormat.setMinimumFractionDigits(2);
        totalDistance = 0.0;
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
        workouDuration = new Duration(0);
    }
    
    private void prepareActionBarMenu() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
            
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                setSelectedValue(itemPosition);
                return true;
            }
        };
        
        actionBar.setListNavigationCallbacks(menuAdapter, navigationListener);
        actionBar.setSelectedNavigationItem(selectedValue);
    }
    
    protected void setSelectedValue(int itemPosition) {
        switch (itemPosition) {
            case Navigate.MAIN:
                break;
            case Navigate.HISTORY:
                Intent intent = new Intent(MainActivity.this, HistoryActivity_.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    
    @AfterViews
    void initializeView() {
        final String methodName = "after";
        Log.d(TAG, "START - " + methodName);
        prepareActionBarMenu();
        clock.setText(timeFormatter.print(DateTime.now()));
        updateClock();
        pauseDrawable.setAlpha(128);
        activitySpinner.setAdapter(iconAdapter);
        kindSpinner.setAdapter(adapter);
        doBindService();
        Log.d(TAG, "END - " + methodName);
    }
    
    @UiThread(delay = 1000)
    protected void updateClock() {
        clock.setText(timeFormatter.print(DateTime.now()));
        if (status == STARTED) {
            workouDuration = workouDuration.plus(1000);
            workoutTime.setText(timeFormatter.print(workouDuration.getMillis()));
        }
        updateClock();
    }
    
    @Trace
    @Click(R.id.start)
    protected void onStartClick() {
        try {
            Message msg = Message.obtain(null, TrackerService.MSG_PROVIDER_CHANGE);
            service.send(msg);
        }
        catch (RemoteException e) {
        }
        if (requested) {
            startLayout.setVisibility(View.GONE);
            stopLayout.setVisibility(View.VISIBLE);
            if (status == PAUSED) { // wznawiamy trening, czyli dodajemy trackSegment
                TrackSegment newTrkSeg = new TrackSegment();
                track.addTrackSegment(newTrkSeg);
            }
            status = STARTED;
        }
        else {
            Toast.makeText(this, getText(R.string.gpsDisabled), Toast.LENGTH_LONG).show();
        }
    }
    
    @Click(R.id.stopWorkout)
    protected void onStopClick() {
        stopLayout.setVisibility(View.GONE);
        startLayout.setVisibility(View.VISIBLE);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Zapiswyanie trasy");
        progressDialog.show();
        saveWorkout(progressDialog);
    }
    
    @Background
    public void saveWorkout(ProgressDialog progressDialog) {
        status = STOPED;
        Route route = new Route();
        route.setDate(new DateTime());
        route.setDistance(totalDistance * 1000);
        route.setName(track.getName());
        route.setTotalTime(System.currentTimeMillis() - firstTime);
        route.setWorkoutTime(workouDuration.getMillis());
        routeManager.saveRoute(route);
        StorageUtil storage = new StorageUtil(this);
        boolean savingResult =
                storage.saveStringToExternalStorage(stream.toXML(gpx), "pobiegne",
                        new DateTime().toString("yyy-MM-dd_HH-mm") + ".gpx", true);
        endSaving(progressDialog, savingResult);
    }
    
    @UiThread
    protected void endSaving(ProgressDialog progressDialog, boolean savingResult) {
        progressDialog.dismiss();
        Intent intent = new Intent(MainActivity.this, HistoryActivity_.class);
        startActivity(intent);
    }
    
    @Trace
    @Click(R.id.statisticFrameLayout)
    protected void onStatisticLayoutClick() {
        onPauseClick();
    }
    
    @Trace
    @Click(R.id.pauseWorkout)
    protected void onPauseClick() {
        if (status == STARTED) {
            status = PAUSED;
            pause.setText(R.string.resume);
            statisticLayout.setForeground(pauseDrawable);
            statisticLayout.requestFocus();
        }
        else {
            status = STARTED;
            pause.setText(R.string.pause);
            statisticLayout.setForeground(null);
            statisticLayout.clearFocus();
        }
    }
    
    @Override
    @Trace
    protected void onPause() {
        super.onPause();
    }
    
    @Override
    @Trace
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    @Trace
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return false;
    }
    
    private void addWaypoint(final Location location) {
        wayPoint =
                new WayPoint(location.getAltitude(), new DateTime(), location.getLongitude(), location.getLatitude());
        trackSegmentList.get(trackSegmentList.size() - 1).addTrackPoint(wayPoint);
    }
    
    void doBindService() {
        bindService(new Intent(this, TrackerService.class), connection, Context.BIND_AUTO_CREATE);
        isBound = true;
    }
    
    void doUnbindService() {
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
    
    
    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TrackerService.MSG_LOCATION_CHANGE:
                    Location recivedLocation = (Location) msg.obj;
                    if (recivedLocation != null) {
                        updateUI(recivedLocation);
                    }
                    break;
                case TrackerService.MSG_PROVIDER_CHANGE:
                    if (msg.arg1 == 1) {
                        requested = true;
                    }
                    else {
                        requested = false;
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
        
        private void updateUI(Location location) {
            gpsState.setText(getResources().getText(R.string.ok));
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
                        currentPace.setText(twoDigitFormat.format(actualPace));
                        avaragePace.setText(twoDigitFormat.format(meanPace));
                    }
                    else {
                        Log.d(TAG, "zbyt duza szybkosc [km/h]=" + actualPace);
                    }
                }
            }
            oldLocation = location;
            distance.setText(threeDigitFormat.format(totalDistance));
            altitude.setText(twoDigitFormat.format(location.getAltitude()));
        }
    }
}