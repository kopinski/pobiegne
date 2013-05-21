package pl.pobiegne.mobile.activity.dialog;

import pl.pobiegne.mobile.R;
import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;


@EActivity(R.layout.gps_dialog_layout)
public class GPSDialog extends Activity {
    
    @AfterViews
    protected void showDialog() {
        getWindow().setTitle(getText(R.string.gpsServiceTitle));
    }
    
    @Click(R.id.cancelDialog)
    protected void cancelClick() {
        finish();
    }
    
    @Click(R.id.settingsDialog)
    protected void settingsClick() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        finish();
    }
}