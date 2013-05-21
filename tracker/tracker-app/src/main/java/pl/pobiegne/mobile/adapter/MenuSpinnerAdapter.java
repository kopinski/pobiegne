package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.IconItemData;
import pl.pobiegne.mobile.navigation.Navigate;
import pl.pobiegne.mobile.widget.MenuSpinnerItem;
import pl.pobiegne.mobile.widget.MenuSpinnerItem_;
import android.view.View;
import android.view.ViewGroup;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.res.StringRes;

@EBean
public class MenuSpinnerAdapter extends BaseSpinnerAdapter {
    
    @StringRes(R.string.running)
    protected String runningLabel;
    
    @StringRes(R.string.cycling)
    protected String cyclingLabel;
    
    
    @Override
    @AfterViews
    protected void initializeIcons() {
        itemDataList = new ArrayList<IconItemData>();
        itemDataList.add(new IconItemData(R.drawable.running, runningLabel, Navigate.MAIN));
        itemDataList.add(new IconItemData(R.drawable.bicycle, cyclingLabel, Navigate.HISTORY));
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuSpinnerItem itemView;
        if (convertView == null) {
            itemView = MenuSpinnerItem_.build(context);
        }
        else {
            itemView = (MenuSpinnerItem) convertView;
        }
        
        itemView.bind(getItem(position));
        
        return itemView;
    }
}