package ch.versusvirus.reddrop.data_access;

import ch.versusvirus.reddrop.logic.model.BloodBarometer;

public interface BloodBarometerParser {
    BloodBarometer parse(String input);
}
