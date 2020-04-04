package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.versusvirus.reddrop.R;

public class SurveyResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);
        findViewById(R.id.button2).setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });

    }

}
