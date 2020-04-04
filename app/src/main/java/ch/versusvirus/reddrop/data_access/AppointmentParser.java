package ch.versusvirus.reddrop.data_access;

import ch.versusvirus.reddrop.logic.model.Appointment;

public interface AppointmentParser {
     Appointment parse(String input);
}
