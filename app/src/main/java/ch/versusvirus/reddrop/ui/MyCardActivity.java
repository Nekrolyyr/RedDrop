package ch.versusvirus.reddrop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.BarcodeGenerator;

public class MyCardActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycard);

        // Obtain donor code from shared preferences
        SharedPreferences sp = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String donorcode = sp.getString("DonorNumber", null);

        // Initialize objects for UI elements
        TextView info = findViewById(R.id.textDonorInfo);
        ImageView barcodeView = findViewById(R.id.barcodeView);
        ImageView barcodeBackground = findViewById(R.id.barcodeBackground);
        Button donatedButton = findViewById(R.id.btn_donated);
        Button cancelButton = findViewById(R.id.btn_cancel);

        // Check if donor code already defined
        if (donorcode == null) { // DONOR ID NOT SET YET
            System.out.println("Donorcode not set yet");

            // Show info text and hide barcode and button
            info.setText("Please enter your donation number in your profile to use this feature.");
            info.setVisibility(View.VISIBLE);
            barcodeView.setVisibility(View.INVISIBLE);
            barcodeBackground.setVisibility(View.INVISIBLE);
            // Set up listener to go to your profile intent
            donatedButton.setVisibility(View.INVISIBLE);
            cancelButton.setText("Go to profile");
            cancelButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            });

        } else { // DONOR ID READY
            // Generate barcode and display
            BarcodeGenerator bcg = new BarcodeGenerator();
            int bgColor = getResources().getColor(R.color.colorPrimary);
            Bitmap barcode = bcg.generateBitmap(donorcode, 600, 200, Color.BLACK, bgColor);
            barcodeView.setVisibility(View.VISIBLE);
            BitmapDrawable bitmapdrawable = new BitmapDrawable(getResources(), barcode);
            barcodeView.setBackground(bitmapdrawable);

            // Set up listener to cancel donation
            cancelButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            });

            // Set up listener to verify donation
            donatedButton.setOnClickListener(v -> {

                // Build alert which confirms confirmation :)
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.confirmDonationDialogContent)
                        .setTitle(R.string.confirmDonationDialogTitle)
                        .setPositiveButton(R.string.confirmDonationDialog, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CONFIRM: Store current date to shared prefs
                                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("LastDonationDate", currentDate);
                                editor.apply();
                            }
                        })
                        .setNegativeButton(R.string.cancelDonationDialog, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // CANCEL: Do nothing
                            }
                        })
                        .create()
                        .show();
            });
        }


    }
}
