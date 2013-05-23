package pl.pobiegne.mobile.db;

import java.sql.SQLException;

import org.joda.time.DateTime;

import pl.pobiegne.mobile.common.api.db.Route;
import pl.pobiegne.mobile.common.api.db.WorkoutType;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    
    /**
     * Nazwa klasy do logowania.
     */
    private final String TAG = this.getClass().getSimpleName();
    
    /**
     * Nazwa bazy danych.
     */
    private static final String DATABASE_NAME = "pobiegne.db";
    
    /**
     * Wersja bazy danych.
     */
    private static final int DATABASE_VERSION = 1;
    
    /**
     * Dao dla skladnikow.
     */
    private Dao<Route, Integer> routetDao = null;
    
    
    public DatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        final String methodName = "onCreate";
        try {
            Log.i(TAG, methodName);
            long millis = System.currentTimeMillis();
            TableUtils.createTable(connectionSource, Route.class);
            
            routetDao = getRouteDao();
            
            Route route = new Route();
            route.setName("Trasa z dnia 11-05-2013");
            route.setCalories(256);
            route.setDate(new DateTime(2013,05,11,0,0));
            route.setTotalTime(1450482);
            route.setDistance(7525.54);
            route.setWorkoutPointsCount(135);
            route.setWorkoutTime(1204584);
            route.setActivity(WorkoutType.CYCLING);
            routetDao.createOrUpdate(route);
            
            Route route2 = new Route();
            route2.setName("Trasa z dnia 11-04-2013");
            route2.setCalories(182);
            route2.setDate(new DateTime(2013,04,11,0,0));
            route2.setWorkoutTime(8204584);
            route2.setActivity(WorkoutType.RUNNING);
            routetDao.createOrUpdate(route2);
            
            Route route3 = new Route();
            route3.setName("Trasa z dnia 08-03-2013");
            route3.setCalories(486);
            route3.setDate(new DateTime(2013,03,8,0,0));
            route3.setActivity(WorkoutType.RUNNING);
            routetDao.createOrUpdate(route3);
            
            Route route4 = new Route();
            route4.setName("Trasa z dnia 01-04-2013");
            route4.setCalories(584);
            route4.setDate(new DateTime(2013,04,1,0,0));
            route4.setActivity(WorkoutType.RUNNING);
            routetDao.createOrUpdate(route4);
            
            Route route5 = new Route();
            route5.setName("Trasa z dnia 09-04-2013");
            route5.setCalories(584);
            route5.setDate(new DateTime(2013,04,9,0,0));
            route5.setActivity(WorkoutType.WALKING);
            routetDao.createOrUpdate(route5);
            
            Route route6 = new Route();
            route6.setName("Trasa z dnia 07-04-2013");
            route6.setCalories(584);
            route6.setDate(new DateTime(2013,04,7,0,0));
            route6.setActivity(WorkoutType.HIKING);
            routetDao.createOrUpdate(route6);
            
            Route route7 = new Route();
            route7.setName("Trasa z dnia 04-04-2013");
            route7.setCalories(584);
            route7.setDate(new DateTime(2013,04,4,0,0));
            route7.setActivity(WorkoutType.RUNNING);
            routetDao.createOrUpdate(route7);
            
            Route route8 = new Route();
            route8.setName("Trasa z dnia 17-04-2013");
            route8.setCalories(584);
            route8.setDate(new DateTime(2013,04,17,0,0));
            route8.setActivity(WorkoutType.RUNNING);
            routetDao.createOrUpdate(route8);
            
            Route route9 = new Route();
            route9.setName("Trasa z dnia 27-04-2013");
            route9.setCalories(584);
            route9.setDate(new DateTime(2013,04,27,0,0));
            route9.setActivity(WorkoutType.RUNNING);
            routetDao.createOrUpdate(route9);
            
            Route route10 = new Route();
            route10.setName("Trasa z dnia 24-04-2013");
            route10.setCalories(584);
            route10.setDate(new DateTime(2013,04,24,0,0));
            route10.setActivity(WorkoutType.RUNNING);
            routetDao.createOrUpdate(route10);
            
            Log.i(TAG, "utworzono nowe rekodry w metodzie " + methodName + " TIME: "
                    + (System.currentTimeMillis() - millis));
        }
        catch (SQLException e) {
            Log.e(TAG, "Nie mozna utowrzyc bazy danych", e);
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
        
    }
    
    /**
     * Zwraca DAO do typu Route.
     */
    public Dao<Route, Integer> getRouteDao() throws SQLException {
        if (routetDao == null) {
            routetDao = getDao(Route.class);
        }
        return routetDao;
    }
    
    /**
     * Zamyka polaczenia i czysci cache bazy oraz obiekty DAO.
     */
    @Override
    public void close() {
        final String methodName = "close";
        super.close();
        routetDao = null;
        Log.i(TAG, methodName + " - zamknieto polaczenia.");
    }
}