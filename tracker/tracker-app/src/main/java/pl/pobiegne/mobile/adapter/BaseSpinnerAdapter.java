package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.IconItemData;
import pl.pobiegne.mobile.common.api.db.WorkoutIntensivity;
import pl.pobiegne.mobile.widget.IconSpinnerItem;
import pl.pobiegne.mobile.widget.IconSpinnerItem_;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.res.StringArrayRes;


@EBean
public class BaseSpinnerAdapter extends BaseAdapter {
    
    @RootContext
    protected Activity context;
    
    @StringArrayRes(R.array.kind)
    protected String[] kind;
    
    protected ArrayList<IconItemData> itemDataList = new ArrayList<IconItemData>();
    
    
    @AfterViews
    protected void initializeIcons() {
        itemDataList.clear();
        itemDataList.add(new IconItemData(kind[0]));
        itemDataList.add(new IconItemData(kind[1]));
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        IconSpinnerItem itemView;
        if (convertView == null) {
            itemView = IconSpinnerItem_.build(context);
        }
        else {
            itemView = (IconSpinnerItem) convertView;
        }
        
        itemView.bind(getItem(position));
        
        return itemView;
    }
    
    @Override
    public int getCount() {
        return itemDataList.size();
    }
    
    @Override
    public IconItemData getItem(int position) {
        return itemDataList.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public WorkoutIntensivity getSelectedIntensivity(int position) {
        return WorkoutIntensivity.values()[itemDataList.get(position).getItem()];
    }
}