package pl.pobiegne.mobile.activity;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.adapter.HistoryAdapter;
import pl.pobiegne.mobile.navigation.NavigationManager;
import pl.pobiegne.mobile.navigation.NavigationManager.Navigate;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Trace;
import com.googlecode.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.ExpandableListView;


@EActivity(R.layout.history_layout)
public class HistoryActivity extends Activity {
    
    @ViewById(R.id.historyList)
    protected ExpandableListView listView;
    
    @Bean
    protected HistoryAdapter adapter;
    
    /** ActionBar Menu */
    @Bean
    protected NavigationManager navigation;
    
    
    @AfterViews
    protected void initiazlizeView() {
        listView.setAdapter(adapter);
        listView.setGroupIndicator(null);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
        navigation.setSelected(Navigate.HISTORY);
    }
    
    @Override
    @Trace
    protected void onResume() {
        super.onResume();
        navigation.buildActionBar(); // tworzenie ActionBar menu
    }
}