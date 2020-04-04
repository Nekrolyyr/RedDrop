package ch.versusvirus.reddrop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import ch.versusvirus.reddrop.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        findViewById(R.id.btn_donation_info).setOnClickListener(view -> {
            // TODO: Donation Info
        });

        ProgressBar zero_p = findViewById(R.id.blood_0p);
        ProgressBar zero_n = findViewById(R.id.blood_0n);
        ProgressBar a_p = findViewById(R.id.blood_Ap);
        ProgressBar a_n = findViewById(R.id.blood_An);
        ProgressBar b_p = findViewById(R.id.blood_Bp);
        ProgressBar b_n = findViewById(R.id.blood_Bn);
        ProgressBar ab_p = findViewById(R.id.blood_ABp);
        ProgressBar ab_n = findViewById(R.id.blood_ABn);
    }
}
