package ch.versusvirus.reddrop.logic.model;

import java.util.Date;

public class AppointmentTimeslot {

    private Date time;
    private int expectedPeople;

    public AppointmentTimeslot(long timeInMillis, int expectedPeople) {
        this.time = new Date(timeInMillis);
        this.expectedPeople = expectedPeople;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getExpectedPeople() {
        return expectedPeople;
    }

    public void setExpectedPeople(int expectedPeople) {
        this.expectedPeople = expectedPeople;
    }
}
