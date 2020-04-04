package ch.versusvirus.reddrop.data_access;

import ch.versusvirus.reddrop.logic.model.AppointmentGetterParams;
import ch.versusvirus.reddrop.logic.model.AppointmentSetterParams;
import okhttp3.Callback;

public interface AppointmentSetterSource {
    void registerAppointmentAction(AppointmentSetterParams params, Callback callback);
}
