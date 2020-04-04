package ch.versusvirus.reddrop.logic.model;

import java.util.Date;

public class AppointmentTimeslot {

    private Date time;
    private int expectedPeople;

    public AppointmentTimeslot(Date time, int expectedPeople) {
        this.time = time;
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
