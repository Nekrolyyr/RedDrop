package ch.versusvirus.reddrop.logic.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Appointment {

    public static final int slotLengthMin = 30;

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

    public List<AppointmentTimeslot> getTimeslots(String startTime) {
        try {
            ArrayList<AppointmentTimeslot> timeslots = new ArrayList<>();
            Calendar timeIterator = Calendar.getInstance();
            timeIterator.setTime(new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(startTime));
            for (int i : remainingCapacities) {
                timeslots.add(new AppointmentTimeslot(timeIterator.getTimeInMillis(), maxCapacityPerSlot - i));
                timeIterator.add(Calendar.MINUTE, slotLengthMin);
            }
            return timeslots;
        } catch (ParseException | NullPointerException e) {
            return Collections.emptyList();
        }
    }

}
