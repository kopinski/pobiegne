package pl.pobiegne.mobile.activity;

import java.util.ArrayList;

import org.joda.time.DateTime;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.adapter.DetailFragmentAdapter;
import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.dao.IRoute;
import pl.pobiegne.mobile.dao.RouteManager;
import pl.pobiegne.mobile.util.StorageUtil;
import pl.pobiegne.mobile.xml.data.Coordinate;
import pl.pobiegne.mobile.xml.data.GPX;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.Trace;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.thoughtworks.xstream.XStream;
import com.viewpagerindicator.TabPageIndicator;


@EActivity(R.layout.details_layout)
@OptionsMenu(R.menu.details_menu)
public class DetailActivity extends FragmentActivity {
    
    @ViewById(R.id.pager)
    protected ViewPager pager;
    
    @ViewById(R.id.indicator)
    protected TabPageIndicator indicator;
    
    @Extra(HistoryActivity.ROUTE_ID)
    protected int routeId;
    
    @Bean(RouteManager.class)
    protected IRoute routeManager;
    
    private DetailFragmentAdapter adapter;
    
    private XStream xstream;
    
    private Route details;
    
    
    @AfterViews
    @Trace
    protected void initialize() {
        details = routeManager.select(routeId);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        ArrayList<LatLng> path = new ArrayList<LatLng>();
        xstream = new XStream();
        xstream.autodetectAnnotations(true);
        xstream.alias("gpx", GPX.class);
        GPX gpx = (GPX) xstream.fromXML(details.getXml());
        coordinates = gpx.getAllCoordinates();
        for (Coordinate coordinate : coordinates) {
            path.add(new LatLng(coordinate.getLatitude(), coordinate.getLongitude()));
        }
        adapter = new DetailFragmentAdapter(getSupportFragmentManager(), path, details);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        
        ActionBar bar = getActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setIcon(R.drawable.view_details);
        bar.setTitle(R.string.detailsTitle);
    }
    
    @OptionsItem
    void homeSelected() {
        finish();
    }
    
    @OptionsItem
    void sendToServerSelected() {
        finish();
    }
    
    @OptionsItem
    @Trace
    protected void saveToFileSelected(MenuItem item) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getText(R.string.routeSaving));
        progressDialog.show();
        saveWorkout(progressDialog, item);
    }
    
    @Background
    @Trace
    protected void saveWorkout(ProgressDialog progressDialog, MenuItem item) {
        StorageUtil storage = new StorageUtil(this);
        String result = details.getXml().replaceFirst("gpx", GPX.GPX_FIRST_LINE);
        
        boolean isSuccess =
                storage.saveStringToExternalStorage(result, "pobiegne", new DateTime().toString("yyy-MM-dd_HH-mm")
                        + ".gpx", true);
        endSaving(progressDialog, isSuccess, item);
    }
    
    @UiThread
    @Trace
    protected void endSaving(ProgressDialog progressDialog, boolean isSuccess, MenuItem item) {
        if (isSuccess) {
            Toast.makeText(this, getText(R.string.saveDone), Toast.LENGTH_LONG).show();
            item.setVisible(false);
        }
        else {
            Toast.makeText(this, getText(R.string.saveDone), Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }
    
    @OptionsItem
    void deleteSelected() {
        new AlertDialog.Builder(this).setTitle(getText(R.string.routeDelete))
                .setMessage(getText(R.string.routeDeleteConfirm)).setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int rows = routeManager.delete(routeId);
                        if (rows > 0) {
                            Toast.makeText(getApplicationContext(), getText(R.string.routeDeleted), Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), getText(R.string.routeDeletionError),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton(R.string.no, null).show();
    }
}