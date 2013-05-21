package pl.pobiegne.mobile.widget;

import android.content.Context;

import com.googlecode.androidannotations.annotations.EViewGroup;

@EViewGroup(resName="comp_spinner_item")
public class MenuSpinnerItem extends IconSpinnerItem {

    public MenuSpinnerItem(Context context) {
        super(context);
    }
}