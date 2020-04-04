package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.versusvirus.reddrop.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.btn_donate).setOnClickListener(v -> {
            startActivity(new Intent(this, SurveyResultActivity.class));
        });
        findViewById(R.id.btn_profile).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        findViewById(R.id.btn_myBloodCenters).setOnClickListener(v -> {
            //TODO: Add donation history / favourites
        });
        findViewById(R.id.btn_info).setOnClickListener(v -> {
            //TODO: info Screen
        });
        findViewById(R.id.btn_loaction).setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });
    }
}
