package org.example.model.session;

import java.sql.Time;
import java.util.Date;

public class CurrentSession {
    private final long sessionID;
    private final String tellerName;
    private final Date startDate;
    private final Date endDate;
    private final Time startTime;
    private final Time endTime;
    private final Time currentTime;
    public CurrentSession(
            long sessionID, String tellerName, Date startDate, Date endDate, Time startTime, Time endTime
    ) {
        this.sessionID = sessionID;
        this.tellerName = tellerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentTime = startTime;
    }
    public long getSessionID() {
        return sessionID;
    }
    public String getTellerName() {
        return tellerName;
    }
    public String setTellerName(String tellerName) {
        return tellerName;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public Time getStartTime() {
        return startTime;
    }
    public Time getEndTime() {
        return endTime;
    }
    public Time getCurrentTime() {
        return currentTime;
    }
}
