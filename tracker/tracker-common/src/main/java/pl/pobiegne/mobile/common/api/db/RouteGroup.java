package pl.pobiegne.mobile.common.api.db;

import java.util.ArrayList;

public class RouteGroup extends Route {

	ArrayList<Route> routes = new ArrayList<Route>();

	public RouteGroup(String name) {
		setName(name);
	}

	/**
	 */
	public ArrayList<Route> getRoutes() {
		return routes;
	}
	/**
	 */
	public void addRoute(Route route) {
		this.routes.add(route);
	}
}