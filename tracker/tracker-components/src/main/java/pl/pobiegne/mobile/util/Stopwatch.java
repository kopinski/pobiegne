package pl.pobiegne.mobile.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Stopwatch {
    
    private long startTime;
    
    private long endTime;
    
    private boolean isRunning;
    
    // lapTimes is able to record all the lap time.
    private CopyOnWriteArrayList<Long> lapTimes;
    
    
    public Stopwatch() {
        isRunning = false;
        lapTimes = new CopyOnWriteArrayList<Long>();
    }
    
    public void start() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis();
        }
        else {
            throw new IllegalStateException("cannot start when running!");
        }
    }
    
    public void stop() {
        if (isRunning) {
            endTime = System.currentTimeMillis();
            lapTimes.add(endTime - startTime);
            isRunning = false;
        }
        else {
            throw new IllegalStateException("cannot stop when not running!");
        }
    }
    
    public void reset() {
        isRunning = false;
        lapTimes.clear();
    }
    
    public List<Long> getLapTime() {
        return lapTimes;
    }
    
    public long getDuration() {
        long totalTime = 0;
        for (Long lap : lapTimes) {
            totalTime += lap;
        }
        if (getLapTime().isEmpty()) { // nie ma miedzyczasow
           return System.currentTimeMillis() - startTime;
        }
        else {
            return totalTime + (System.currentTimeMillis() - startTime);
        }
    }
}