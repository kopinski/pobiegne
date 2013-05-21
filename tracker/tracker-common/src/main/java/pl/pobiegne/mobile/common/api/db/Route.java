package pl.pobiegne.mobile.common.api.db;

import org.joda.time.DateTime;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Route")
public class Route {
    
    /**
     * Klucz podstawowy.
     */
    @DatabaseField(generatedId = true)
    private int id;
    
    /**
     * Nazwa trasy.
     */
    @DatabaseField
    private String name;
    
    /**
     * Data zapisu trasy.
     */
    @DatabaseField
    private DateTime date;
    
    /**
     * Przebyty dystans.
     */
    @DatabaseField
    private double distance;
    
    /**
     * Spalone kalorie.
     */
    @DatabaseField
    private int calories;
    
    /**
     * Czas treningu z pauzami.
     */
    @DatabaseField
    private long totalTime;
    
    /**
     * Liczba punktow pomniarowych.
     */
    @DatabaseField
    private int workoutPointsCount;
    
    /**
     * Czas treningu bez pauz.
     */
    @DatabaseField
    private long workoutTime;
    
    /**
     * Czy wyslano na serwer.
     */
    @DatabaseField
    private boolean uploaded;
    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the date
     */
    public DateTime getDate() {
        return date;
    }
    
    /**
     * @param date the date to set
     */
    public void setDate(DateTime date) {
        this.date = date;
    }
    
    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }
    
    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    /**
     * @return the calories
     */
    public int getCalories() {
        return calories;
    }
    
    /**
     * @param calories the calories to set
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
    /**
     * @return the totalTime
     */
    public long getTotalTime() {
        return totalTime;
    }
    
    /**
     * @param totalTime the totalTime to set
     */
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
    
    /**
     * @return the workoutPointsCount
     */
    public int getWorkoutPointsCount() {
        return workoutPointsCount;
    }
    
    /**
     * @param workoutPointsCount the workoutPointsCount to set
     */
    public void setWorkoutPointsCount(int workoutPointsCount) {
        this.workoutPointsCount = workoutPointsCount;
    }
    
    /**
     * @return the workoutTime
     */
    public long getWorkoutTime() {
        return workoutTime;
    }
    
    /**
     * @param workoutTime the workoutTime to set
     */
    public void setWorkoutTime(long workoutTime) {
        this.workoutTime = workoutTime;
    }

	/**
	 * @return the uploaded
	 */
	public boolean isUploaded() {
		return uploaded;
	}

	/**
	 * @param uploaded the uploaded to set
	 */
	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}
}
