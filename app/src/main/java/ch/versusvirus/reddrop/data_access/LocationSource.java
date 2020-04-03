package ch.versusvirus.reddrop.data_access;

import ch.versusvirus.reddrop.logic.model.LocationSearchParams;
import okhttp3.Callback;

public interface LocationSource {
    void getLocations(LocationSearchParams params, Callback callback);
}
