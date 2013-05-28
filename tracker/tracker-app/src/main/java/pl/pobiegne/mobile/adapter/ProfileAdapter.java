package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.IconItemData;
import pl.pobiegne.mobile.preference.TrackerPreferences_;
import pl.pobiegne.mobile.widget.IconLabelItem;
import pl.pobiegne.mobile.widget.IconLabelItem_;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;


@EBean
public class ProfileAdapter extends BaseAdapter {
    
    @RootContext
    protected Activity context;
    
    @Pref
    TrackerPreferences_ pref;
    
    protected ArrayList<IconItemData> itemDataList = new ArrayList<IconItemData>();
    
    
    @AfterViews
    public void initialize() {
        itemDataList.clear();
        itemDataList.add(new IconItemData(R.drawable.tear_of_calendar, context.getString(R.string.birthday), pref
                .birhtday().get(), ProfileItem.BIRTHDAY.ordinal()));
        itemDataList.add(new IconItemData(R.drawable.height, context.getString(R.string.height), pref.height().get()
                + " " + context.getText(R.string.cm), ProfileItem.HEIGHT.ordinal()));
        itemDataList.add(new IconItemData(R.drawable.scale, context.getString(R.string.weight), pref.weight().get()
                + " " + context.getText(R.string.kg), ProfileItem.WEIGHT.ordinal()));
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        IconLabelItem itemView;
        if (convertView == null) {
            itemView = IconLabelItem_.build(context);
        }
        else {
            itemView = (IconLabelItem) convertView;
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
    
    
    public enum ProfileItem {
        BIRTHDAY, HEIGHT, WEIGHT, SEX;
    }
}
