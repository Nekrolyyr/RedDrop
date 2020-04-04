package ch.versusvirus.reddrop.logic.model;

import java.sql.Time;

public class AppointmentTimeslot {

    private Time time;
    private int expectedPeople;

    public AppointmentTimeslot(Time time, int expectedPeople) {
        this.time = time;
        this.expectedPeople = expectedPeople;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getExpectedPeople() {
        return expectedPeople;
    }

    public void setExpectedPeople(int expectedPeople) {
        this.expectedPeople = expectedPeople;
    }
}
