package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.IconItemData;
import pl.pobiegne.mobile.common.api.db.WorkoutType;
import android.content.res.TypedArray;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.res.StringArrayRes;


@EBean
public class IconSpinnerAdapter extends BaseSpinnerAdapter {
    
    @StringArrayRes(R.array.activtiy)
    protected String[] activities;
    
    
    @Override
    @AfterViews
    protected void initializeIcons() {
        TypedArray activitiesImg = context.getResources().obtainTypedArray(R.array.activtiy_imgs);
        itemDataList = new ArrayList<IconItemData>();
        itemDataList.add(new IconItemData(activitiesImg.getResourceId(0, -1), activities[0], WorkoutType.RUNNING
                .ordinal()));
        itemDataList.add(new IconItemData(activitiesImg.getResourceId(1, -1), activities[1], WorkoutType.CYCLING
                .ordinal()));
        itemDataList.add(new IconItemData(activitiesImg.getResourceId(2, -1), activities[2], WorkoutType.HIKING
                .ordinal()));
        itemDataList.add(new IconItemData(activitiesImg.getResourceId(3, -1), activities[3], WorkoutType.WALKING
                .ordinal()));
    }
    
    public WorkoutType getSelectedWorkoutType(int position) {
        return WorkoutType.values()[itemDataList.get(position).getItem()];
    }
}