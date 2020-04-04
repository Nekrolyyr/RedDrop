package ch.versusvirus.reddrop.data_access;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ch.versusvirus.reddrop.logic.model.BloodBarometer;
import ch.versusvirus.reddrop.logic.model.BloodBarometerParams;
import ch.versusvirus.reddrop.logic.model.DonationListEntry;
import ch.versusvirus.reddrop.logic.model.LocationSearchParams;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class RemoteLoader {

    public static void getLocationsAsync(LocationSearchParams searchParams, RemoteLoaderResult<List<DonationListEntry>> resultAction) {
        if (searchParams == null || !searchParams.isComplete()) {
            return;
        }
        LocationSource source = new OkHttpSource();
        source.getLocations(searchParams, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                resultAction.put(Collections.emptyList());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.body() == null) {
                    resultAction.put(Collections.emptyList());
                } else {
                    LocationParser parser = new DonationListParser();
                    resultAction.put(parser.parse(response.body().string()));
                }
            }
        });
    }

    public static void getBloodBarometerAsync(BloodBarometerParams searchParams, RemoteLoaderResult<BloodBarometer> resultAction) {
        if (searchParams == null) {
            return;
        }
        BloodBarometerSource source = new OkHttpSource();
        source.getBloodStatus(searchParams, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                resultAction.put(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.body() == null) {
                    resultAction.put(null);
                } else {
                    BloodBarometerParser parser = new BloodonorBarometerParser();
                    resultAction.put(parser.parse(response.body().string()));
                }
            }
        });
    }

    public interface RemoteLoaderResult<T> {
        void put(T result);
    }
}
