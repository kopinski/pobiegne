package pl.pobiegne.mobile.dialog;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.activity.ProfileActivity;
import android.app.Activity;
import android.content.Intent;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;


@EActivity(R.layout.number_picker_dialog_layout)
public class NumberPickerDialog extends Activity {
    
    @ViewById(R.id.dialogNumberPicker)
    protected NumberPicker picker;
    
    @Extra(ProfileActivity.DIALOG_REQUEST_CODE)
    int requestCode = -1;
    
    @Extra(ProfileActivity.INITIAL_VALUE)
    int initaialValue = -1;
    
    
    @AfterViews
    protected void showDialog() {
        getWindow().setTitle(getText(R.string.numberSelect));
        if (requestCode == ProfileActivity.CHANGE_HEIGHT) {
            picker.setMinValue(100);
            picker.setMaxValue(250);
            picker.setValue(initaialValue);
        }
        else if (requestCode == ProfileActivity.CHANGE_WEIGHT) {
            picker.setMinValue(20);
            picker.setMaxValue(250);
            picker.setValue(initaialValue);
        }
        else {
            Toast.makeText(this, getText(R.string.errorNumberPicker), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    @Click(R.id.cancelDialog)
    protected void cancelClick() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
    
    @Click(R.id.settingsDialog)
    protected void settingsClick() {
        Intent intent = new Intent();
        intent.putExtra("result", picker.getValue());
        setResult(RESULT_OK, intent);
        finish();
    }
}