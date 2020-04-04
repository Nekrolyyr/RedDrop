package ch.versusvirus.reddrop.data_access;

import ch.versusvirus.reddrop.logic.model.AppointmentGetterParams;
import ch.versusvirus.reddrop.logic.model.LocationSearchParams;
import okhttp3.Callback;

public interface AppointmentGetterSource {
    void getAppointmentStatus(AppointmentGetterParams params, Callback callback);
}
