package pl.pobiegne.mobile.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.db.DatabaseHelper;
import android.util.Log;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;


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
    public boolean saveRoute(Route route) {
        final String methodName = "saveRoute";
        Log.i(TAG, "START: " + methodName);
        long time = System.currentTimeMillis();
        
        try {
            routetDao.create(route);
        }
        catch (SQLException e) {
            Log.e(TAG, methodName + " - blad przy zapisywaniu trasy. " + e.getMessage());
            return false;
        }
        Log.i(TAG, "END: " + methodName + " TIME: " + (System.currentTimeMillis() - time));
        return true;
    }
    
    @Override
    public Route select(int id) {
        final String methodName = "select";
        Log.i(TAG, "START: " + methodName);
        long time = System.currentTimeMillis();
        
        Route route = new Route();
        try {
            route = (Route) routetDao.queryBuilder().where().eq("id", id).queryForFirst();
        }
        catch (SQLException e) {
            Log.e(TAG, methodName + " - blad przy pobieraniu trasy. " + e.getMessage());
            e.printStackTrace();
        }
        
        Log.i(TAG, "END: " + methodName + " TIME: " + (System.currentTimeMillis() - time));
        return route;
    }
    
    @Override
    public int delete(int id) {
        final String methodName = "delete";
        Log.i(TAG, "START: " + methodName);
        long time = System.currentTimeMillis();
        
        int rows = 0;
        try {
            DeleteBuilder<Route, Integer> db = routetDao.deleteBuilder();
            db.where().eq("id", id);
            rows = routetDao.delete(db.prepare());
        }
        catch (SQLException e) {
            Log.e(TAG, methodName + " - blad przy usuwaniu trasy. " + e.getMessage());
            e.printStackTrace();
        }
        
        Log.i(TAG, "END: " + methodName + " TIME: " + (System.currentTimeMillis() - time));
        return rows;
    }
}