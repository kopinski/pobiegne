package pl.pobiegne.mobile.navigation;

import pl.pobiegne.mobile.R;
import pl.pobiegne.mobile.activity.HistoryActivity_;
import pl.pobiegne.mobile.activity.MainActivity_;
import pl.pobiegne.mobile.activity.ProfileActivity_;
import pl.pobiegne.mobile.adapter.MenuSpinnerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.res.StringRes;


@EBean
public class NavigationManager {
    
    @RootContext
    protected Activity context;
    
    @Bean
    protected MenuSpinnerAdapter menuAdapter;
    
    @StringRes(R.string.yes)
    protected String yes;
    
    @StringRes(R.string.no)
    protected String no;
    
    @StringRes(R.string.confirmExit)
    protected String confirm;
    
    private Navigate selected;
    
    private ActionBar actionBar;
    
    
    @AfterViews
    protected void prepareActionBar() {
        actionBar = context.getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
            
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                setSelectedValue(itemPosition);
                return true;
            }
        };
        actionBar.setListNavigationCallbacks(menuAdapter, navigationListener);
    }
    
    public void buildActionBar() {
        actionBar.setSelectedNavigationItem(selected.ordinal());
    }
    
    private void setSelectedValue(int itemPosition) {
        if (selected.ordinal() != itemPosition) {
            Intent intent;
            switch (Navigate.values()[itemPosition]) {
                case MAIN:
                    intent = new Intent(context, MainActivity_.class);
                    context.startActivity(intent);
                    break;
                case HISTORY:
                    intent = new Intent(context, HistoryActivity_.class);
                    context.startActivity(intent);
                    break;
                case PROFILE:
                    intent = new Intent(context, ProfileActivity_.class);
                    context.startActivity(intent);
                    break;
                case EXIT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
                    builder.setMessage(confirm).setCancelable(false)
                            .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                                
                                public void onClick(DialogInterface dialog, int id) {
                                    context.finish();
                                    System.exit(0);
                                }
                            }).setNegativeButton(no, new DialogInterface.OnClickListener() {
                                
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    buildActionBar(); // powrot do poprzedniej pozycji z menu
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
                default:
                    break;
            }
        }
    }
    
    public Navigate getSelected() {
        return selected;
    }
    
    public void setSelected(Navigate selected) {
        this.selected = selected;
    }
    
    
    public enum Navigate {
        MAIN, HISTORY, PROFILE, EXIT,
    }
}