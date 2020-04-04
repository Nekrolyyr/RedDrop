package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.versusvirus.reddrop.R;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        findViewById(R.id.btn_submit).setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });
    }
}
