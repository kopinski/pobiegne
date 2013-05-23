package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.IconItemData;
import pl.pobiegne.mobile.common.api.db.WorkoutType;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.res.StringRes;


@EBean
public class IconSpinnerAdapter extends BaseSpinnerAdapter {
    
    @StringRes(R.string.running)
    protected String runningLabel;
    
    @StringRes(R.string.cycling)
    protected String cyclingLabel;
    
    @StringRes(R.string.walking)
    protected String walkingLabel;
    
    @StringRes(R.string.hiking)
    protected String hikingLabel;
    
    
    @Override
    @AfterViews
    protected void initializeIcons() {
        itemDataList = new ArrayList<IconItemData>();
        itemDataList.add(new IconItemData(R.drawable.comp_running, runningLabel, WorkoutType.RUNNING.ordinal()));
        itemDataList.add(new IconItemData(R.drawable.comp_bicycle, cyclingLabel, WorkoutType.CYCLING.ordinal()));
        itemDataList.add(new IconItemData(R.drawable.comp_trekking, hikingLabel, WorkoutType.HIKING.ordinal()));
        itemDataList.add(new IconItemData(R.drawable.comp_walking, walkingLabel, WorkoutType.WALKING.ordinal()));
    }
    
    public WorkoutType getSelectedWorkout(int position) {
        return WorkoutType.values()[itemDataList.get(position).getItem()];
    }
}