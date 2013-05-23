package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.IconItemData;
import pl.pobiegne.mobile.navigation.NavigationManager.Navigate;
import pl.pobiegne.mobile.widget.MenuSpinnerItem;
import pl.pobiegne.mobile.widget.MenuSpinnerItem_;
import android.view.View;
import android.view.ViewGroup;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.res.StringRes;


@EBean
public class MenuSpinnerAdapter extends BaseSpinnerAdapter {
    
    @StringRes(R.string.menu_workout)
    protected String workout;
    
    @StringRes(R.string.menu_history)
    protected String history;
    
    @StringRes(R.string.menu_profile)
    protected String profile;
    
    @StringRes(R.string.menu_exit)
    protected String exit;
    
    
    @Override
    @AfterViews
    protected void initializeIcons() {
        itemDataList = new ArrayList<IconItemData>();
        itemDataList.add(new IconItemData(R.drawable.menu_logo, workout, Navigate.MAIN.ordinal()));
        itemDataList.add(new IconItemData(R.drawable.today, history, Navigate.HISTORY.ordinal()));
        itemDataList.add(new IconItemData(R.drawable.edit_profile, profile, Navigate.PROFILE.ordinal()));
        itemDataList.add(new IconItemData(R.drawable.exit, exit, Navigate.EXIT.ordinal()));
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