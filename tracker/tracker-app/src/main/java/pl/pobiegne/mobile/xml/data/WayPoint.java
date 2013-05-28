package pl.pobiegne.mobile.xml.data;

import org.joda.time.DateTime;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("trkpt")
public class WayPoint {
    
    @XStreamAlias("time")
    private DateTime time;
    
    @XStreamAlias("ele")
    private double elevation;
    
    @XStreamAsAttribute
    private double lon = 0.0;
    
    @XStreamAsAttribute
    private double lat = 0.0;
    
    public WayPoint() {
    }
    
    public WayPoint(final double elevation, final DateTime time, final double lon, final double lat) {
        this.elevation = elevation;
        this.time = time;
        this.lon = lon;
        this.lat = lat;
        
    }
    
    /**
     * @return the longitude
     */
    public double getLongitude() {
        return lon;
    }
    
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.lon = longitude;
    }
    
    /**
     * @return the latitude
     */
    public double getLatitude() {
        return lat;
    }
    
    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.lat = latitude;
    }
}