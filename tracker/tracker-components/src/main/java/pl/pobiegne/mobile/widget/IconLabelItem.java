package pl.pobiegne.mobile.widget;

import pl.pobiegne.mobile.common.api.IconItemData;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;


@EViewGroup(resName = "comp_icon_item")
public class IconLabelItem extends LinearLayout {
    
    
    @ViewById(resName="comp_item_icon")
    protected ImageView icon;
    
    @ViewById(resName="comp_upper_label")
    protected TextView upper;
    
    @ViewById(resName="comp_lower_label")
    protected TextView lower;
    
    
    public IconLabelItem(Context context) {
        super(context);
    }
    
    public void bind(final IconItemData data) {
        this.upper.setText(data.getLabel());
        this.lower.setText(data.getSubLabel());
        if (data.getIcon() != 0) {
            this.icon.setVisibility(View.VISIBLE);
            this.icon.setBackgroundResource(data.getIcon());
        }
        else {
            this.icon.setVisibility(View.INVISIBLE);
        }
    }
}