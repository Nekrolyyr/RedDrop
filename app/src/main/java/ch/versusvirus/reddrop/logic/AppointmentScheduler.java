package ch.versusvirus.reddrop.logic;

import android.content.Context;

import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.model.AppointmentSetterParams;
import ch.versusvirus.reddrop.logic.model.DonationListEntry;

public abstract class AppointmentScheduler {
    public static void scheduleAt(DonationListEntry location, int position, Context context) {
        RemoteLoader.registerAppointmentActionAsync(new AppointmentSetterParams.Builder().id(location.getId()).slotIdx(position).action("schedule").build(), result -> {
            if (result.getSuccess()) {
                Reminder reminder = new Reminder(context);
                reminder.scheduleNotification(location, "08:00");
                //TODO save appointment
                //TODO reload notifications on boot
            }
        });
    }
}
