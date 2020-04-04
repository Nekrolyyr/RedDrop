package ch.versusvirus.reddrop.data_access;

import ch.versusvirus.reddrop.logic.model.BloodBarometerParams;
import ch.versusvirus.reddrop.logic.model.LocationSearchParams;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

class OkHttpSource implements LocationSource, BloodBarometerSource {
    private static final String locationsURL = "https://www.blutspende.ch/de/blutspendetermine/terminliste?search[term]={{term}}&search[radius]={{radius}}";
    private static final String bloodBarometerURL = "http://bloodonor.julien.li/API/get_supplies.php?region={{region}}";

    private OkHttpClient client;

    public OkHttpSource() {
        client = new OkHttpClient();
    }

    @Override
    public void getLocations(LocationSearchParams params, Callback callback) {
        Request request = new Request.Builder()
                .url(buildLocationURL(params))
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    private String buildLocationURL(LocationSearchParams params) {
        String URL = locationsURL.replace("{{term}}", params.getPLZ()).replace("{{radius}}", params.getRadius());
        System.out.println("HTTP GET TO " + URL);
        return URL;
    }

    @Override
    public void getBloodStatus(BloodBarometerParams params, Callback callback) {
        Request request = new Request.Builder()
                .url(buildBloodBarometerURL(params))
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    private String buildBloodBarometerURL(BloodBarometerParams params) {
        return bloodBarometerURL.replace("{{region}}", params.getLocation());
    }
}
