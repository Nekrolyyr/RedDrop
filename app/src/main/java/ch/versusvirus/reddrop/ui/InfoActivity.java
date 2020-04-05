package ch.versusvirus.reddrop.ui;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.Reminder;
import ch.versusvirus.reddrop.logic.model.BloodBarometer;
import ch.versusvirus.reddrop.logic.model.BloodBarometerEntry;
import ch.versusvirus.reddrop.logic.model.BloodBarometerParams;
import ch.versusvirus.reddrop.logic.model.BloodLevels;
import ch.versusvirus.reddrop.logic.model.Regions;

public class InfoActivity extends AppCompatActivity {

    private ProgressBar zero_p;
    private ProgressBar zero_n;
    private ProgressBar a_p;
    private ProgressBar a_n;
    private ProgressBar b_p;
    private ProgressBar b_n;
    private ProgressBar ab_p;
    private ProgressBar ab_n;

    private TextView lastUpdated;

    private Reminder reminder;
    private int notification = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar myToolbar = findViewById(R.id.my_toolbar_info);
        setSupportActionBar(myToolbar);
        setTitle("Information");

        reminder = new Reminder(getApplicationContext());

        zero_p = findViewById(R.id.blood_0p);
        zero_n = findViewById(R.id.blood_0n);
        a_p = findViewById(R.id.blood_Ap);
        a_n = findViewById(R.id.blood_An);
        b_p = findViewById(R.id.blood_Bp);
        b_n = findViewById(R.id.blood_Bn);
        ab_p = findViewById(R.id.blood_ABp);
        ab_n = findViewById(R.id.blood_ABn);

        lastUpdated = findViewById(R.id.txt_blood_lastUpdated);
        setupSpinner();

        setupButtons();
    }

    private void setupSpinner() {
        Spinner spinner = findViewById(R.id.spinner_blood_region);
        List<String> regions = new ArrayList<>(Regions.REGIONS.values());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, regions);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getBloodData((String) Regions.REGIONS.keySet().toArray()[i]);
                ((TextView) adapterView.getChildAt(0)).setTextSize(12);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupButtons() {
        Button donationInfo = findViewById(R.id.btn_donation_info);
        Button whyDonate = findViewById(R.id.btn_why_donate);
        Button magazine = findViewById(R.id.btn_magazine);

        donationInfo.setOnClickListener(view -> goToUrl("https://www.blutspende.ch/de/blutspende"));
        whyDonate.setOnClickListener(view -> goToUrl("https://www.blutspende.ch/de/spenderinfos/warum-blut-spenden"));
        magazine.setOnClickListener(view -> goToUrl("https://www.blutspende.ch/de/magazin"));
    }

    private void getBloodData(String location) {
        RemoteLoader.getBloodBarometerAsync(new BloodBarometerParams.Builder().location(location).build(),
                result -> runOnUiThread(() -> submitBloodData(result, Regions.REGIONS.get(location))));
    }

    private void submitBloodData(BloodBarometer bloodMeter, String location) {
        BloodBarometerEntry entry = bloodMeter.getCurrentState();

        setProgress(zero_p, entry.getNullPositive(), location, "0+");
        setProgress(zero_n, entry.getNullNegative(), location, "0-");
        setProgress(a_p, entry.getaPositive(), location, "A+");
        setProgress(a_n, entry.getaNegative(), location, "A-");
        setProgress(b_p, entry.getbPositive(), location, "B+");
        setProgress(b_n, entry.getbNegative(), location, "B-");
        setProgress(ab_p, entry.getAbPositive(), location, "AB+");
        setProgress(ab_n, entry.getAbNegative(), location, "AB-");

        lastUpdated.setText(entry.getDate());
    }

    private void setProgress(ProgressBar bar, int bloodLevel, String location, String bloodType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bar.setProgress(mapBloodLevelToPercent(bloodLevel, location, bloodType), true);
        } else {
            bar.setProgress(mapBloodLevelToPercent(bloodLevel, location, bloodType));
        }
    }

    private int mapBloodLevelToPercent(int level, String location, String bloodType) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        BloodLevels bloodLevel = BloodLevels.values()[level];
        switch (bloodLevel) {
            case CriticallyLow:
            case Critical:
                String[] messageCritical = getBloodLowMessage(location, bloodType, bloodLevel);
                Notification notification1 = reminder.bloodReserveNotification(messageCritical[0], messageCritical[1]);
                notificationManager.notify(notificationNumber(), notification1);
                break;
        }
        return BloodLevels.getPercent(bloodLevel);
    }

    private String[] getBloodLowMessage(String location, String bloodType, BloodLevels level) {
        String title = "BloodCenter " + location;
        String message = "Blood reserves from type " + bloodType + " are " + level.toString();
        return new String[]{title, message};
    }

    private int notificationNumber() {
        return notification++;
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
