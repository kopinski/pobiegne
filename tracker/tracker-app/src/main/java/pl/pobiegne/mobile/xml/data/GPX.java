package pl.pobiegne.mobile.xml.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("gpx")
public class GPX {
    
    public final static String GPX_XMLNS = "http://www.topografix.com/GPX/1/1";
    
    public final static String GPX_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    
    public final static String GPX_SCHEMA =
            "http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd";
    
    public final static String GPX_FIRST_LINE =
            "gpx xmlns:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.topografix.com/GPX/1/1\" creator=\"Pobiegne Tracker http://pobiegne.pl\" version=\"1.1\"";
    
    // @XStreamImplicit
    // private double version;
    //
    // @XStreamImplicit
    // private String creator;
    //
    // @XStreamImplicit
    // private MetaData metaData;
    
    @XStreamImplicit
    private ArrayList<Track> tracks = new ArrayList<Track>();
    
    
    public GPX() {
        // this.version = 1.1;
        // this.creator = "Pobiegne Tracker http://pobiegne.pl";
    }
    
    /**
     * @return the map
     */
    public Map<String, String> getHashMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("xmlns:xsi", GPX.GPX_XSI);
        map.put("xmlns:schemaLocation", GPX.GPX_SCHEMA);
        map.put("xmlns", GPX.GPX_XMLNS);
        return map;
    }
    
    /**
     * @return the tracks
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }
    
    /**
     * @param tracks the tracks to set
     */
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
    
    /**
     * @param tracks the tracks to set
     */
    public void addTrack(Track track) {
        this.tracks.add(track);
    }
    
    public ArrayList<Coordinate> getAllCoordinates() {
        ArrayList<Coordinate> path = new ArrayList<Coordinate>();
        for (TrackSegment segemnt : tracks.get(0).getTrackSegments()) {
            for (WayPoint wayPoint : segemnt.getTrackPoints()) {
                path.add(new Coordinate(wayPoint.getLongitude(), wayPoint.getLatitude()));
            }
        }
        return path;
    }
}