package ch.versusvirus.reddrop.ui;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.Reminder;
import ch.versusvirus.reddrop.logic.model.BloodBarometer;
import ch.versusvirus.reddrop.logic.model.BloodBarometerEntry;
import ch.versusvirus.reddrop.logic.model.BloodBarometerParams;

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

    private String[] regions = new String[]{"gesamt", "zuerich", "graubuenden"};

    private Reminder reminder;
    private int notification = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        reminder = new Reminder(getApplicationContext());

        findViewById(R.id.btn_donation_info).setOnClickListener(view -> {
            // TODO: Donation Info
        });

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
    }

    private void setupSpinner() {
        Spinner spinner = findViewById(R.id.spinner_blood_region);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, regions);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getBloodData(regions[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getBloodData(String location) {
        RemoteLoader.getBloodBarometerAsync(new BloodBarometerParams.Builder().location(location).build(),
                result -> runOnUiThread(() -> submitBloodData(result, location)));
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
        switch (level) {
            case 0:
                String[] messageCritical = getBloodLowMessage(location, bloodType, "critical");
                Notification notification1 = reminder.bloodReserveNotification(messageCritical[0], messageCritical[1]);
                notificationManager.notify(notificationNumber(), notification1);
                return 1;
            case 1:
                String[] messageLow = getBloodLowMessage(location, bloodType, "low");
                Notification notification2 = reminder.bloodReserveNotification(messageLow[0], messageLow[1]);
                notificationManager.notify(notificationNumber(), notification2);
                return 10;
            case 2:
                return 25;
            case 3:
                return 60;
            case 4:
                return 80;
            default:
                return 0;
        }
    }

    private String[] getBloodLowMessage(String location, String bloodType, String severity) {
        String title = "BloodCenter " + location;
        String message = "Blood reserves from type " + bloodType + " are " + severity;
        return new String[]{title, message};
    }

    private int notificationNumber(){
        return notification++;
    }
}
