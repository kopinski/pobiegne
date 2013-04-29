package pl.pobiegne.mobile.xml.data;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("trk")
public class Track {
    
    @XStreamImplicit
    private ArrayList<TrackSegment> trackSegments = new ArrayList<TrackSegment>();
    
    @XStreamAlias("name")
    private String name;
    
    
    public Track() {
    }
    
    /**
     * @return the trackSegments
     */
    public ArrayList<TrackSegment> getTrackSegments() {
        return trackSegments;
    }
    
    /**
     * @param trackSegments the trackSegments to set
     */
    public void setTrackSegments(ArrayList<TrackSegment> trackSegments) {
        this.trackSegments = trackSegments;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}