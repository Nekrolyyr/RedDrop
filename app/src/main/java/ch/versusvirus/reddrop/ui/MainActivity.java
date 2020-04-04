package ch.versusvirus.reddrop.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
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
    }
}
