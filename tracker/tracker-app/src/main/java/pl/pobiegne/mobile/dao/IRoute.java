package pl.pobiegne.mobile.dao;

import java.util.ArrayList;

import pl.pobiegne.mobile.common.api.db.Route;


public interface IRoute {
    
    /**
     * Metoda pobierajaca z bazy wszystkie trasy.
     * 
     * @return Lista tras.
     */
    ArrayList<Route> selectAll();
    
    /**
     * Metoda pobierajaca z bazy szczegoly trasy.
     */
    Route select(int id);
    
    /**
     * Metoda usuwajaca z bazy trase o wskazanym id.
     */
    int delete(int id);
    
    boolean saveRoute(Route route);
}