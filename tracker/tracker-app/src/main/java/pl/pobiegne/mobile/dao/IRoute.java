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
    
    void saveRoute(Route route);
}