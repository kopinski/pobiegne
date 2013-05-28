package pl.pobiegne.mobile.widget;

import java.util.Locale;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.pobiegne.mobile.common.api.db.Route;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;


@EViewGroup(resName = "child_group_item")
public class ChildGroupItem extends LinearLayout {
    
    @ViewById(resName = "comp_child_name")
    protected TextView name;
    
    @ViewById(resName = "comp_bottom_left")
    protected TextView bottomLeft;
    
    @ViewById(resName = "comp_bottom_center")
    protected TextView bottomCenter;
    
    @ViewById(resName = "comp_bottom_right")
    protected TextView bottomRight;
    
    @ViewById(resName = "comp_child_icon")
    protected ImageView leftIcon;
    
    @ViewById(resName = "comp_right_icon")
    protected ImageView rightIcon;
    
    /**
     * Formater godziny.
     */
    private DateTimeFormatter timeFormatter;
    
    
    public ChildGroupItem(Context context) {
        super(context);
        timeFormatter = DateTimeFormat.forPattern("HH:mm:ss").withZoneUTC();
    }
    
    public void bind(final Route data) {
        name.setText(data.getName());
        bottomLeft.setText(data.getCalories() + "");
        bottomCenter.setText(timeFormatter.print(data.getWorkoutTime()));
        bottomRight.setText(String.format(Locale.getDefault(), "%.2f", (data.getDistance()) / 1000) + " km");
        if (data.isUploaded()) {
            rightIcon.setBackgroundResource(0);
        }
        else {
            rightIcon.setBackgroundResource(R.drawable.comp_upload);
        }
        if (data.getActivity() != null) {
            switch (data.getActivity()) {
                case RUNNING:
                    leftIcon.setBackgroundResource(R.drawable.comp_running);
                    break;
                case CYCLING:
                    leftIcon.setBackgroundResource(R.drawable.comp_bicycle);
                    break;
                case HIKING:
                    leftIcon.setBackgroundResource(R.drawable.comp_trekking);
                    break;
                case WALKING:
                    leftIcon.setBackgroundResource(R.drawable.comp_walking);
                    break;
                default:
                    leftIcon.setBackgroundResource(R.drawable.comp_default_activity);
                    break;
            }
        }
        else {
            leftIcon.setBackgroundResource(R.drawable.comp_default_activity);
        }
    }
}