package ch.versusvirus.reddrop.logic.model;

public class Appointment {

    private String id;
    private int nSlots, maxCapacityPerSlot;
    private int[] remainingCapacities;
    private Boolean success;
    private String action;

    public Appointment(String id, int nSlots, int maxCapacityPerSlot,
                       int[] remainingCapacities) {
        this.id = id;
        this.nSlots = nSlots;
        this.maxCapacityPerSlot = maxCapacityPerSlot;
        this.remainingCapacities = remainingCapacities;
        this.success = null;
        this.action = null;


    }

    public Appointment(String id, int nSlots, int maxCapacityPerSlot,
                       int[] remainingCapacities, boolean success, String action) {
        this.id = id;
        this.nSlots = nSlots;
        this.maxCapacityPerSlot = maxCapacityPerSlot;
        this.remainingCapacities = remainingCapacities;
        this.success = success;
        this.action = action;

    }

    public String getId() {
        return id;
    }

    public int getnSlots() {
        return nSlots;
    }

    public int getMaxCapacityPerSlot() {
        return maxCapacityPerSlot;
    }

    public int[] getRemainingCapacities() {
        return remainingCapacities;
    }

    public Boolean getSuccess() { return success; }

    public String getAction() { return action; }

}
