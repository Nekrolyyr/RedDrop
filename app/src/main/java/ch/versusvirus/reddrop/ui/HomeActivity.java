package ch.versusvirus.reddrop.ui;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import java.text.MessageFormat;
import java.util.Map;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.Reminder;
import ch.versusvirus.reddrop.logic.model.BloodBarometer;
import ch.versusvirus.reddrop.logic.model.BloodBarometerParams;
import ch.versusvirus.reddrop.logic.model.BloodLevels;
import ch.versusvirus.reddrop.logic.model.Regions;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.findViewById(R.id.btn_toolbar_home).setOnClickListener(v -> startActivity(new Intent(this, WelcomeActivity.class)));

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

        SharedPreferences sp = getSharedPreferences(ProfileActivity.MyPREFERENCES, MODE_PRIVATE);
        String region = sp.getString("Region", "Overall");
        String bloodType = sp.getString("BloodType", "A+");

        TextView bloodTypeText = findViewById(R.id.txt_home_blood_type);
        TextView bloodRegion = findViewById(R.id.txt_home_location);
        bloodTypeText.setText(bloodType);
        bloodRegion.setText(region);
        getBloodData(mapRegion(region), bloodType);
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
            case "A+":
            default:
                bloodLevelIndex = result.getCurrentState().getaPositive();
                break;
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

    private String mapRegion(String regionValue){
        for (Map.Entry<String, String> entry : Regions.REGIONS.entrySet()){
            if(entry.getValue().equals(regionValue)){
                return entry.getKey();
            }
        }
        return (String) Regions.REGIONS.keySet().toArray()[0];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_donor_card:

                break;
            case R.id.action_notifications:
                startActivity(new Intent(this, NotificationsActivity.class));
                break;
            case R.id.action_share:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
