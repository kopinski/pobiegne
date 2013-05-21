package pl.pobiegne.mobile.xml.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("gpx")
public class GPX {
    
    public final static String GPX_XMLNS = "http://www.topografix.com/GPX/1/1";
    
    public final static String GPX_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    
    public final static String GPX_SCHEMA =
            "http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd";
    
    @XStreamAsAttribute
    private double version;
    
    @XStreamAsAttribute
    private String creator;
    
    @XStreamAlias("metaData")
    private MetaData metaData;
    
    @XStreamImplicit
    private ArrayList<Track> tracks = new ArrayList<Track>();
    
    
    public GPX() {
        this.version = 1.1;
        this.creator = "Pobiegne Tracker http://pobiegne.pl";
    }
    
    /**
     * @return the metaData
     */
    public MetaData getMetaData() {
        return metaData;
    }
    
    /**
     * @param metaData the metaData to set
     */
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
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
}