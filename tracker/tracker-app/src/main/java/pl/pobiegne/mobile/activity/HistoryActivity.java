package pl.pobiegne.mobile.activity;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.adapter.HistoryAdapter;
import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.navigation.NavigationManager;
import pl.pobiegne.mobile.navigation.NavigationManager.Navigate;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Trace;
import com.googlecode.androidannotations.annotations.ViewById;


@EActivity(R.layout.history_layout)
public class HistoryActivity extends Activity {
    
    public static final String ROUTE_ID = "routeId";
    
    @ViewById(R.id.historyList)
    protected ExpandableListView listView;
    
    @Bean
    protected HistoryAdapter adapter;
    
    private ProgressDialog progressDialog;
    
    /** ActionBar Menu */
    @Bean
    protected NavigationManager navigation;
    
    private OnChildClickListener onChildClickListener = new OnChildClickListener() {
        
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            progressDialog.show();
            Intent intent = new Intent(HistoryActivity.this, DetailActivity_.class);
            Route route = adapter.getChild(groupPosition, childPosition);
            intent.putExtra(ROUTE_ID, route.getId());
            startActivity(intent);
            return false;
        }
    };
    
    
    @AfterViews
    protected void initiazlizeView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getText(R.string.routeLoading));
        listView.setAdapter(adapter);
        listView.setGroupIndicator(null);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
        listView.setOnChildClickListener(onChildClickListener);
        navigation.setSelected(Navigate.HISTORY);
    }
    
    @Override
    @Trace
    protected void onResume() {
        super.onResume();
        adapter.initAdapter();
        adapter.notifyDataSetChanged();
        navigation.buildActionBar(); // tworzenie ActionBar menu
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.cancel();
    }
}