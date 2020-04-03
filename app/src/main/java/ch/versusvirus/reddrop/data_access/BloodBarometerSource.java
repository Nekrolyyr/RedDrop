package ch.versusvirus.reddrop.data_access;

import ch.versusvirus.reddrop.logic.model.BloodBarometerParams;
import okhttp3.Callback;

public interface BloodBarometerSource {
    void getBloodStatus(BloodBarometerParams params, Callback callback);
}
