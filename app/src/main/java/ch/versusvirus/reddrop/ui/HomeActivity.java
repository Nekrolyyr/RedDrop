package ch.versusvirus.reddrop.ui;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import java.text.MessageFormat;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.Reminder;
import ch.versusvirus.reddrop.logic.model.BloodBarometer;
import ch.versusvirus.reddrop.logic.model.BloodBarometerParams;
import ch.versusvirus.reddrop.logic.model.BloodLevels;

public class HomeActivity extends AppCompatActivity {

    private Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("HOME");

        reminder = new Reminder(getApplicationContext());
        findViewById(R.id.btn_notification_test).setOnClickListener(v -> {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            String title = "Blood Donation Request";
            String content = "URGENT: Any blood from people with Covid-19 needed.";
            Intent intent = new Intent(this, HomeActivity.class);
            intent = intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Notification notification = reminder.specialNotification(title, content, intent);
            notificationManager.notify(1, notification);
        });

        findViewById(R.id.btn_donate).setOnClickListener(v -> {
            startActivity(new Intent(this, SurveyActivity.class));
        });
        findViewById(R.id.btn_profile).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        findViewById(R.id.btn_myBloodCenters).setOnClickListener(v -> {
            //TODO: Add donation history / favourites
        });
        findViewById(R.id.btn_info).setOnClickListener(v -> {
            startActivity(new Intent(this, InfoActivity.class));
        });
        findViewById(R.id.btn_loaction).setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });

        getBloodData("zuerich", "A+");
    }

    private void getBloodData(String location, String bloodType) {
        RemoteLoader.getBloodBarometerAsync(new BloodBarometerParams.Builder().location(location).build(),
                result -> runOnUiThread(() -> submitBloodData(result, bloodType)));
    }

    private void submitBloodData(BloodBarometer result, String bloodType) {
        ProgressBar bloodLevel = findViewById(R.id.blood_level_home);
        TextView bloodLevelPercent = findViewById(R.id.txt_home_blood_level);
        TextView bloodLevelStatus = findViewById(R.id.txt_home_blood_status);

        int bloodLevelIndex = -1;

        switch (bloodType) {
            case "0+":
                bloodLevelIndex = result.getCurrentState().getNullPositive();
                break;
            case "0-":
                bloodLevelIndex = result.getCurrentState().getNullNegative();
                break;
            case "A+":
                bloodLevelIndex = result.getCurrentState().getaPositive();
                break;
            case "A-":
                bloodLevelIndex = result.getCurrentState().getaNegative();
                break;
            case "B+":
                bloodLevelIndex = result.getCurrentState().getbPositive();
                break;
            case "B-":
                bloodLevelIndex = result.getCurrentState().getbNegative();
                break;
            case "AB+":
                bloodLevelIndex = result.getCurrentState().getAbPositive();
                break;
            case "AB-":
                bloodLevelIndex = result.getCurrentState().getAbNegative();
                break;
            default:
                throw new RuntimeException("Bloodtype unkown");
        }

        int bloodPercent = BloodLevels.getPercent(BloodLevels.values()[bloodLevelIndex]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bloodLevel.setProgress(bloodPercent, true);
        } else {
            bloodLevel.setProgress(bloodPercent);
        }
        bloodLevelPercent.setText(MessageFormat.format("{0} %", bloodPercent));
        bloodLevelStatus.setText(BloodLevels.values()[bloodLevelIndex].toString());
    }
}
