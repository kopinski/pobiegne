package pl.pobiegne.mobile.adapter;

import java.util.ArrayList;

import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.fragment.DetailFragment;
import pl.pobiegne.mobile.fragment.MapDetailFragment_;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.android.gms.maps.model.LatLng;


public class DetailFragmentAdapter extends FragmentStatePagerAdapter {
    
    private static final String[] TITLES = new String[] {"SZCZAGÓŁY", "MAPA"};
    
    private ArrayList<LatLng> path = new ArrayList<LatLng>();
    
    private Route route;
    
    
    public DetailFragmentAdapter(FragmentManager fm, ArrayList<LatLng> path, Route route) {
        super(fm);
        this.path = path;
        this.route = route;
    }
    
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = DetailFragment.newInstance(route);
                break;
            case 1:
                fragment = MapDetailFragment_.newInstance(path);
                break;
        }
        return fragment;
    }
    
    @Override
    public int getCount() {
        return TITLES.length;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position].toUpperCase();
    }
}