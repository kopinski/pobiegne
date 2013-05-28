package pl.pobiegne.mobile.activity;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Years;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.adapter.ProfileAdapter;
import pl.pobiegne.mobile.adapter.ProfileAdapter.ProfileItem;
import pl.pobiegne.mobile.common.api.IconItemData;
import pl.pobiegne.mobile.common.api.UserData;
import pl.pobiegne.mobile.dialog.NumberPickerDialog_;
import pl.pobiegne.mobile.navigation.NavigationManager;
import pl.pobiegne.mobile.navigation.NavigationManager.Navigate;
import pl.pobiegne.mobile.preference.TrackerPreferences_;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.OnActivityResult;
import com.googlecode.androidannotations.annotations.Trace;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;


@EActivity(R.layout.profile_layout)
public class ProfileActivity extends Activity {
    
    public static final String DIALOG_REQUEST_CODE = "requestCode";
    
    public static final String INITIAL_VALUE = "initialValue";
    
    public static final String NUMBER_PICKER_DIALOG_RESULT = "result";
    
    /** Zmiana wzrostu ciala */
    public static final int CHANGE_HEIGHT = 0;
    
    /** Zmiana masy ciala */
    public static final int CHANGE_WEIGHT = 1;
    
    @ViewById(R.id.profileListView)
    protected ListView listView;
    
    @Bean
    protected ProfileAdapter adapter;
    
    /** ActionBar Menu */
    @Bean
    protected NavigationManager navigation;
    
    /** ActionBar Menu */
    @Pref
    protected TrackerPreferences_ pref;
    
    private UserData user;
    
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            DateTime date = new DateTime(selectedYear, selectedMonth + 1, selectedDay, 0, 0);
            user.setBirthday(date);
            pref.birhtday().put(date.toString("yyyy-MM-dd", Locale.getDefault()));
            pref.age().put(Years.yearsBetween(date, new DateTime()).getYears());
            adapter.initialize();
            adapter.notifyDataSetChanged();
        }
    };
    
    
    @AfterViews
    protected void initiazlizeView() {
        user =
                new UserData(DateTime.parse(pref.birhtday().get()), pref.weight().get(), pref.height().get(), pref
                        .isMale().get());
        listView.setAdapter(adapter);
        navigation.setSelected(Navigate.PROFILE);
    }
    
    @Override
    @Trace
    protected void onResume() {
        super.onResume();
        navigation.buildActionBar(); // tworzenie ActionBar menu
    }
    
    @ItemClick(R.id.profileListView)
    protected void onListClick(IconItemData data) {
        int selectedItem = data.getItem();
        switch (ProfileItem.values()[selectedItem]) {
            case BIRTHDAY:
                changeBirthDay();
                break;
            case HEIGHT:
                changeHeight();
                break;
            case WEIGHT:
                changeWeight();
                break;
            
            default:
                break;
        }
    }
    
    private void changeBirthDay() {
        String birthday = pref.birhtday().get();
        DateTime date = new DateTime(DateTime.parse(birthday));
        DatePickerDialog datepicker =
                new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, datePickerListener, date.getYear(),
                        date.getMonthOfYear() - 1, date.getDayOfMonth());
        datepicker.show();
    }
    
    private void changeHeight() {
        Intent intent = new Intent(this, NumberPickerDialog_.class);
        intent.putExtra(INITIAL_VALUE, pref.height().get());
        intent.putExtra(DIALOG_REQUEST_CODE, CHANGE_HEIGHT);
        startActivityForResult(intent, CHANGE_HEIGHT);
    }
    
    private void changeWeight() {
        Intent intent = new Intent(this, NumberPickerDialog_.class);
        intent.putExtra(INITIAL_VALUE, pref.weight().get());
        intent.putExtra(DIALOG_REQUEST_CODE, CHANGE_WEIGHT);
        startActivityForResult(intent, CHANGE_WEIGHT);
    }
    
    @OnActivityResult(CHANGE_HEIGHT)
    void onHeightResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int height = data.getIntExtra(NUMBER_PICKER_DIALOG_RESULT, pref.height().get());
            user.setHeight(height);
            pref.height().put(height);
            adapter.initialize();
            adapter.notifyDataSetChanged();
        }
    }
    
    @OnActivityResult(CHANGE_WEIGHT)
    void onWeightResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int weight = data.getIntExtra(NUMBER_PICKER_DIALOG_RESULT, pref.weight().get());
            user.setWeight(weight);
            pref.weight().put(weight);
            adapter.initialize();
            adapter.notifyDataSetChanged();
        }
    }
}