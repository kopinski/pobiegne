package pl.pobiegne.mobile.xml.data;

public class Coordinate {
    
    private double longitude = 0.0;
    
    private double latitude = 0.0;
    
    
    public Coordinate(final double lon, final double lat) {
        this.latitude = lat;
        this.longitude = lon;
    }
    
    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }
    
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }
    
    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
}
