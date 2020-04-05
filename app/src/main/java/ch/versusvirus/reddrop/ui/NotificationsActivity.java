package ch.versusvirus.reddrop.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import ch.versusvirus.reddrop.R;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar myToolbar = findViewById(R.id.my_toolbar_notification);
        setSupportActionBar(myToolbar);
        setTitle("NotificationsActivity");


    }
}
