package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.common.api.IconItemData;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.res.StringRes;


@EBean
public class IconSpinnerAdapter extends BaseSpinnerAdapter {
    
    @StringRes(R.string.running)
    protected String runningLabel;
    
    @StringRes(R.string.cycling)
    protected String cyclingLabel;
    
    
    @Override
    @AfterViews
    protected void initializeIcons() {
        itemDataList = new ArrayList<IconItemData>();
        itemDataList.add(new IconItemData(R.drawable.running, runningLabel));
        itemDataList.add(new IconItemData(R.drawable.bicycle, cyclingLabel));
    }
}
