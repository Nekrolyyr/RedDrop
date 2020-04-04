package ch.versusvirus.reddrop.logic.model;

import java.util.List;

public class BloodBarometer {

    private String date, region;
    private BloodBarometerEntry currentState;
    private List<BloodBarometerEntry> stateHistory;

    public BloodBarometer(String region, BloodBarometerEntry currentState,
                          List<BloodBarometerEntry> stateHistory) {
        this.region = region;
        this.date = currentState.getDate();
        this.currentState = currentState;
        this.stateHistory = stateHistory;
    }

    public BloodBarometerEntry getCurrentState() {
        return this.currentState;
    }

    public List<BloodBarometerEntry> getFullStateHistory() {
        return this.stateHistory;
    }

    public String getRegion() {
        return this.region;
    }

    public String getDate() {
        return this.date;
    }

}
