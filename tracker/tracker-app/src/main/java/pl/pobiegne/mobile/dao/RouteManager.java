package pl.pobiegne.mobile.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.db.DatabaseHelper;
import android.util.Log;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.j256.ormlite.dao.Dao;


/**
 * Klasa do operacji na bazie danych w tabeli Route.
 * 
 * @author Krzysztof Kopinski
 */
@EBean
public class RouteManager implements IRoute {
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    /**
     * DAO do tras.
     */
    @OrmLiteDao(helper = DatabaseHelper.class, model = Route.class)
    Dao<Route, Integer> routetDao;
    
    @Override
    public ArrayList<Route> selectAll() {
        final String methodName = "selectAll";
        Log.i(TAG, "START: " + methodName);
        long time = System.currentTimeMillis();
        
        ArrayList<Route> routes = new ArrayList<Route>();
        try {
            routes = (ArrayList<Route>) routetDao.queryBuilder().where().not().eq("name", "").query();
        }
        catch (SQLException e) {
            Log.e(TAG, methodName + " - blad przy pobieraniu listy wszystkich tras. " + e.getMessage());
            e.printStackTrace();
        }
        
        Log.i(TAG, "END: " + methodName + " TIME: " + (System.currentTimeMillis() - time));
        return routes;
    }


    @Override
    public void saveRoute(Route route) {
        final String methodName = "saveRoute";
        Log.i(TAG, "START: " + methodName);
        long time = System.currentTimeMillis();
        
        try {
            routetDao.create(route);
        }
        catch (SQLException e) {
            Log.e(TAG, methodName + " - blad przy zapisywaniu trasy. " + e.getMessage());
            e.printStackTrace();
        }
        
        Log.i(TAG, "END: " + methodName + " TIME: " + (System.currentTimeMillis() - time));
    }
}