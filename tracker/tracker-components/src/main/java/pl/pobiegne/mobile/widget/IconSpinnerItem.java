package pl.pobiegne.mobile.widget;

import pl.pobiegne.mobile.common.api.IconItemData;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.icon_spinner_item)
public class IconSpinnerItem extends RelativeLayout {
    
    @ViewById(R.id.conp_icon_spinner)
    protected ImageView icon;
    
    @ViewById(R.id.comp_spinner_label)
    protected TextView label;
    
    
    public IconSpinnerItem(Context context) {
        super(context);
    }
    
    public void bind(final IconItemData data) {
        this.label.setText(data.getLabel());
        if (data.getIcon() != 0) {
            this.icon.setVisibility(View.VISIBLE);
            this.icon.setBackgroundResource(data.getIcon());
        }
        else {
            this.icon.setVisibility(View.GONE);
        }
    }
}
