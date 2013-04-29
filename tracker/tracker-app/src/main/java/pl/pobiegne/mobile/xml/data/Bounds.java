package pl.pobiegne.mobile.xml.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("bounds")
public class Bounds {
    
    @XStreamAsAttribute
    private double minlat;
    
    @XStreamAsAttribute
    private double maxlat;
    
    @XStreamAsAttribute
    private double minlon;
    
    @XStreamAsAttribute
    private double maxlon;
    
    
    public Bounds(final double minlat, final double maxlat, final double minlon, final double maxlon) {
        this.minlat = minlat;
        this.maxlat = maxlat;
        this.minlon = minlon;
        this.maxlon = maxlon;
    }
    
    /**
     * @return the minlat
     */
    public double getMinlat() {
        return minlat;
    }
    
    /**
     * @param minlat the minlat to set
     */
    public void setMinlat(double minlat) {
        this.minlat = minlat;
    }
    
    /**
     * @return the maxlat
     */
    public double getMaxlat() {
        return maxlat;
    }
    
    /**
     * @param maxlat the maxlat to set
     */
    public void setMaxlat(double maxlat) {
        this.maxlat = maxlat;
    }
    
    /**
     * @return the minlon
     */
    public double getMinlon() {
        return minlon;
    }
    
    /**
     * @param minlon the minlon to set
     */
    public void setMinlon(double minlon) {
        this.minlon = minlon;
    }
    
    /**
     * @return the maxlon
     */
    public double getMaxlon() {
        return maxlon;
    }
    
    /**
     * @param maxlon the maxlon to set
     */
    public void setMaxlon(double maxlon) {
        this.maxlon = maxlon;
    }
    
}
