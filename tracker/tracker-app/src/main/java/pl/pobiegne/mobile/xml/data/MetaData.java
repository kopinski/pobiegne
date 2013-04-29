package pl.pobiegne.mobile.xml.data;

import org.joda.time.DateTime;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("metadata")
public class MetaData {
    
    @XStreamAlias("name")
    private String name;
    
    @XStreamAlias("time")
    private DateTime time;
    
    @XStreamAlias("bounds")
    private Bounds bounds;
    
    public MetaData() {
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
    
    /**
     * @return the time
     */
    public DateTime getTime() {
        return time;
    }
    
    /**
     * @param time the time to set
     */
    public void setTime(DateTime time) {
        this.time = time;
    }
    
    /**
     * @return the bounds
     */
    public Bounds getBounds() {
        return bounds;
    }
    
    /**
     * @param bounds the bounds to set
     */
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }
}
