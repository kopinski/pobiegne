package pl.pobiegne.mobile.adapter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.common.api.db.RouteGroup;
import pl.pobiegne.mobile.dao.IRoute;
import pl.pobiegne.mobile.dao.RouteManager;
import pl.pobiegne.mobile.widget.ChildGroupItem;
import pl.pobiegne.mobile.widget.ChildGroupItem_;
import pl.pobiegne.mobile.widget.GroupItem;
import pl.pobiegne.mobile.widget.GroupItem_;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.ViewById;


@EBean
public class HistoryAdapter extends BaseExpandableListAdapter {
    
    @RootContext
    protected Context context;
    
    @Bean(RouteManager.class)
    protected IRoute itemManager;
    
    @ViewById(R.id.history_row_left)
    protected TextView totalWorkoutDistance;
    
    @ViewById(R.id.history_row_center)
    protected TextView totalWorkoutTime;
    
    @ViewById(R.id.history_row_right)
    protected TextView totalWorkoutCalories;
    
    private ArrayList<Route> items = new ArrayList<Route>();
    
    private ArrayList<RouteGroup> groups = new ArrayList<RouteGroup>();
    
    
    @AfterViews
    public void initAdapter() {
        items.clear();
        groups.clear();
        items = itemManager.selectAll();
        if (items != null) {
            Collections.sort(items);
            DateTime end = new DateTime().dayOfMonth().withMaximumValue();
            DateTime start =
                    new DateTime(end.getYear(), end.getMonthOfYear(), end.dayOfMonth().withMinimumValue()
                            .getDayOfMonth(), 0, 0);
            int sortedCount = 0;
            double totalDistance = 0.0;
            long totalDuration = 0;
            int totalCalories = 0;
            for (int i = 0; i < 60; i++) { // sumowanie czasu i dystansu przy okazji
                if (sortedCount < items.size()) {
                    SimpleDateFormat dateNameFormat = new SimpleDateFormat("LLLL yyyy");
                    RouteGroup routeGroup = new RouteGroup(dateNameFormat.format(start.toDate()));
                    double groupDistance = 0.0;
                    long groupDuration = 0;
                    int groupCalories = 0;
                    for (Route route : items) {
                        if (new Interval(start, end).contains(route.getDate())) {
                            routeGroup.addRoute(route);
                            sortedCount++;
                            groupDistance += route.getDistance();
                            groupDuration += route.getWorkoutTime();
                            groupCalories += route.getCalories();
                        }
                    }
                    if (routeGroup.getRoutes().size() > 0) { // dodajemy treningi do listy
                        routeGroup.setCalories(groupCalories);
                        routeGroup.setDistance(groupDistance);
                        routeGroup.setWorkoutTime(groupDuration);
                        totalDistance += groupDistance;
                        totalDuration += groupDuration;
                        totalCalories += groupCalories;
                        groups.add(routeGroup);
                    }
                    end = end.minusMonths(1);
                    start = start.minusMonths(1);
                }
                else {
                    NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
                    totalWorkoutCalories.setText(numberFormat.format(totalCalories));
                    totalWorkoutDistance.setText(numberFormat.format(totalDistance / 1000) + " km");
                    totalWorkoutTime.setText(String.format("%02d:%02d:%02d", totalDuration / 3600000,
                            totalDuration / 60000 % 60, totalDuration / 1000 % 60));
                    break; // zatrzymuje w przypadku trenigu sprzed 5 lat (60 miesiecy)
                }
            }
        }
    }
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupItem view;
        if (convertView != null)
            view = (GroupItem) convertView;
        else
            view = GroupItem_.build(parent.getContext());
        
        Route route = groups.get(groupPosition);
        view.bind(route);
        
        return view;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
            ViewGroup parent) {
        ChildGroupItem view;
        if (convertView != null)
            view = (ChildGroupItem) convertView;
        else
            view = ChildGroupItem_.build(parent.getContext());
        
        Route route = groups.get(groupPosition).getRoutes().get(childPosition);
        view.bind(route);
        
        return view;
    }
    
    @Override
    public int getGroupCount() {
        return groups.size();
    }
    
    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getRoutes().size();
    }
    
    @Override
    public RouteGroup getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }
    
    @Override
    public Route getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getRoutes().get(childPosition);
    }
    
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    @Override
    public boolean hasStableIds() {
        return false;
    }
    
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}