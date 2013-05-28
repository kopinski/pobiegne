package pl.pobiegne.mobile.preference;

import com.googlecode.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import com.googlecode.androidannotations.annotations.sharedpreferences.DefaultInt;
import com.googlecode.androidannotations.annotations.sharedpreferences.DefaultString;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref.Scope;


@SharedPref(value=Scope.UNIQUE)
public interface TrackerPreferences {
    
    @DefaultString("1980-01-01")
    String birhtday();
    
    @DefaultInt(33)
    int age();
    
    @DefaultInt(75)
    int weight();
    
    @DefaultInt(175)
    int height();
    
    @DefaultBoolean(true)
    boolean isMale();
}