package pl.pobiegne.mobile.activity;

import pl.pobiegne.mobile.R;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;


@EActivity(R.layout.main_layout)
public class MainActivity extends Activity {
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    @ViewById(R.id.textView1)
    protected TextView textView;
    
    
    @AfterViews
    void after() {
        final String methodName = "after";
        Log.d(TAG, "START - " + methodName);

        Log.d(TAG, "END - " + methodName);
    }
}