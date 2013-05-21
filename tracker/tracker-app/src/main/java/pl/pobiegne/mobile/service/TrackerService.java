package pl.pobiegne.mobile.service;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.activity.MainActivity_;
import pl.pobiegne.mobile.activity.dialog.GPSDialog_;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;


public class TrackerService extends Service implements LocationListener {
    
    /**
     * Nazwa klasy do logowania.
     */
//    private final String TAG = this.getClass().getSimpleName();
    
    public static final int MSG_LOCATION_CHANGE = 0;
    
    public static final int MSG_PROVIDER_CHANGE = 1;
    
    public static final int MSG_REGISTER_CLIENT = 2;
    
    public static final long MIN_TIME_UPDATE = 0;
    
    public static final long MIN_DITANCE_UPDATE = 0;
    
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    
    protected LocationManager locationManager;
    
    private NotificationManager notificationManager;
    
    private Messenger client;
    
    private boolean gpsProviderEnabled;
    
    /**
     * Czy zlecono uaktualnianie pozycji?
     */
    private boolean requested;
    
    
    @Override
    public void onLocationChanged(Location location) {
        try {
            client.send(Message.obtain(null, MSG_LOCATION_CHANGE, location));
        }
        catch (RemoteException e) {
        }
    }
    
    @Override
    public void onProviderDisabled(String provider) {
        try {
            client.send(Message.obtain(null, MSG_PROVIDER_CHANGE, 0, 0));
        }
        catch (RemoteException e) {
        }
    }
    
    @Override
    public void onProviderEnabled(String provider) {
        try {
            client.send(Message.obtain(null, MSG_PROVIDER_CHANGE, 1, 0));
        }
        catch (RemoteException e) {
        }
    }
    
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    
    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        showNotification();
        runLocator();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        locationManager.removeUpdates(this);
        notificationManager.cancel(R.string.app_name);
        return super.onUnbind(intent);
    }
    
    @Override
    public void onDestroy() {
//        Toast.makeText(this, R.string.stop, Toast.LENGTH_SHORT).show();
    }
    
    private void showNotification() {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity_.class), 0);
        
        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker(getText(R.string.app_name));
        builder.setSmallIcon(R.drawable.app_noticifaction);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(contentIntent);
        builder.setContentText(getText(R.string.notificationContent));
        builder.setContentTitle(getText(R.string.app_name));
        notificationManager.notify(R.string.notificationTicker, builder.getNotification());
    }
    
    public void runLocator() {
        try {
            gpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            Toast.makeText(this, String.valueOf(gpsProviderEnabled), Toast.LENGTH_SHORT).show();
            
            if (gpsProviderEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE,
                        MIN_DITANCE_UPDATE, this);
                requested = true;
                client.send(Message.obtain(null, MSG_PROVIDER_CHANGE, 1, 0));
            }
            else {
                Intent intent = new Intent(TrackerService.this, GPSDialog_.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        catch (Exception e) {
        }
    }
    
    
    /**
     * Handler of incoming messages from clients.
     */
    private class IncomingHandler extends Handler {
        
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
//                    Toast.makeText(getApplicationContext(), "zarejstrowano", Toast.LENGTH_SHORT).show();
                    client = msg.replyTo;
                    break;
                case MSG_PROVIDER_CHANGE:
                    if (requested) {
                        msg.arg1 = 1;
                    }
                    else {
                        msg.arg1 = 0;
                    }
                    try {
                        client.send(Message.obtain(null, MSG_PROVIDER_CHANGE, msg.arg1, msg.arg2));
                    }
                    catch (RemoteException e) {
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}