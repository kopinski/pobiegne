package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.IconItemData;
import pl.pobiegne.mobile.widget.IconSpinnerItem;
import pl.pobiegne.mobile.widget.IconSpinnerItem_;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;


@EBean
public class BaseSpinnerAdapter extends BaseAdapter {
    
    @RootContext
    protected Activity context;
    
    protected ArrayList<IconItemData> itemDataList = new ArrayList<IconItemData>();
    
    
    @AfterViews
    protected void initializeIcons() {
        itemDataList.add(new IconItemData(context.getResources().getText(R.string.aerobic).toString()));
        itemDataList.add(new IconItemData(context.getResources().getText(R.string.interval).toString()));
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
}
