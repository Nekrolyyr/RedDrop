package ch.versusvirus.reddrop.ui;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.Reminder;

public class HomeActivity extends AppCompatActivity {

    private Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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
    }
}
