package pl.pobiegne.mobile.widget;

import java.text.NumberFormat;
import java.util.Locale;

import pl.pobiegne.mobile.common.api.db.Route;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;


@EViewGroup(resName = "group_item")
public class GroupItem extends LinearLayout {
    
    @ViewById(resName = "comp_group_name")
    protected TextView name;
    
    @ViewById(resName = "comp_group_label")
    protected TextView rightLabel;
    
    private NumberFormat numberFormat;
    
    
    public GroupItem(Context context) {
        super(context);
        numberFormat = NumberFormat.getIntegerInstance(Locale.getDefault());
    }
    
    public void bind(final Route data) {
        name.setText(data.getName());
        rightLabel.setText(numberFormat.format(data.getCalories()));
    }
}