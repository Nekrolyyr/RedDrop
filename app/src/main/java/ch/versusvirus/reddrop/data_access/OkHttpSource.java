package ch.versusvirus.reddrop.data_access;

import ch.versusvirus.reddrop.logic.model.AppointmentGetterParams;
import ch.versusvirus.reddrop.logic.model.AppointmentSetterParams;
import ch.versusvirus.reddrop.logic.model.BloodBarometerParams;
import ch.versusvirus.reddrop.logic.model.LocationSearchParams;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

class OkHttpSource implements LocationSource, BloodBarometerSource, AppointmentGetterSource, AppointmentSetterSource {
    private static final String locationsURL = "https://www.blutspende.ch/de/blutspendetermine/terminliste?search{{term}}=8057&search{{radius}}=100";
    private static final String bloodBarometerURL = "https://bloodonor.julien.li/API/get_supplies.php?region={{region}}";
    private static final String appointmentGetterURL = "https://bloodonor.julien.li/API/get_schedule_capacities.php?passwd={{password}}&n_slots={{nSlots}}&id={{id}}";
    private static final String appointmentSetterURL = "https://bloodonor.julien.li/API/schedule_action.php";

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

    @Override
    public void getAppointmentStatus(AppointmentGetterParams params, Callback callback) {
        Request request = new Request.Builder()
                .url(buildAppointmentGetterURL(params))
                .get()
                .build();
        client.newCall(request).enqueue(callback);

    }

    private String buildAppointmentGetterURL(AppointmentGetterParams params) {
        String s = appointmentGetterURL.replace("{{nSlots}}", Integer.toString(params.getnSlots()));
        s = s.replace("{{id}}", params.getId());
        return s.replace("{{password}}", params.getPassword());
    }

    @Override
    public void registerAppointmentAction(AppointmentSetterParams params, Callback callback) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("passwd", params.getPassword())
                .addFormDataPart("id", params.getId())
                .addFormDataPart("slot_idx", Integer.toString(params.getSlotIdx()))
                .addFormDataPart("action", params.getAction())
                .build();

        Request request = new Request.Builder()
                .url(appointmentSetterURL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
