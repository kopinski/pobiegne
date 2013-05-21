package pl.pobiegne.mobile.xml.data;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("trkseg")
public class TrackSegment {
    
    @XStreamImplicit
    private ArrayList<WayPoint> trackPoints = new ArrayList<WayPoint>();
    
    
    public TrackSegment() {
    }
    
    /**
     * @return the trackPoints
     */
    public ArrayList<WayPoint> getTrackPoints() {
        return trackPoints;
    }
    
    /**
     * @param trackPoints the trackPoints to set
     */
    public void setTrackPoints(ArrayList<WayPoint> trackPoints) {
        this.trackPoints = trackPoints;
    }
    
    /**
     * @param trackPoints the trackPoints to set
     */
    public void addTrackPoint(WayPoint trackPoint) {
        this.trackPoints.add(trackPoint);
    }
}