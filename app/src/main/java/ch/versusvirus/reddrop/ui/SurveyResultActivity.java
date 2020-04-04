package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.versusvirus.reddrop.R;

public class SurveyResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);

        findViewById(R.id.btn_donate_now).setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });
        findViewById(R.id.btn_redo).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        findViewById(R.id.home).setOnClickListener(v -> {
            //TODO: Add donation history / favourites
        });

    }
}
