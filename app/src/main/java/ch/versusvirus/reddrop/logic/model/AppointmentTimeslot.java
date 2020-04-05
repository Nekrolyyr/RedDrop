package ch.versusvirus.reddrop.logic.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    @Override
    public String toString() {
        return new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(time);
    }
}
