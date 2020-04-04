package ch.versusvirus.reddrop.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.model.AppointmentGetterParams;
import ch.versusvirus.reddrop.logic.model.AppointmentSetterParams;
import ch.versusvirus.reddrop.logic.model.DonationListEntry;
import ch.versusvirus.reddrop.logic.model.LocationSearchParams;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RemoteLoader.getLocationsAsync(new LocationSearchParams.Builder().PLZ("8645").radius("100").build(), result -> {
            for (DonationListEntry donationListEntry : result) {
                System.out.println(donationListEntry.getDate() + " " + donationListEntry.getVillageInfo());
            }
        });

        RemoteLoader.getAppointmentStatusAsync(new AppointmentGetterParams.Builder().id("lele").nSlots(10).build(), result -> {
            for (int i : result.getRemainingCapacities()) {
                System.out.println(result.getId() + " " + Integer.toString(i));
            }
        });

        RemoteLoader.registerAppointmentActionAsync(new AppointmentSetterParams.Builder().id("lele").slotIdx(0).action("schedule").build(), result -> {
            for (int i : result.getRemainingCapacities()) {
                System.out.println(result.getId() + " " + Integer.toString(i));
            }
        });
    }
}
