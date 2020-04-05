package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.ToolbarActionManager;
import ch.versusvirus.reddrop.logic.model.DonationListEntry;
import ch.versusvirus.reddrop.logic.model.LocationSearchParams;
import ch.versusvirus.reddrop.ui.adapter.LocationEntryAdapter;

import static ch.versusvirus.reddrop.ui.ProfileActivity.MyPREFERENCES;

public class LocationActivity extends AppCompatActivity {

    private static final String defaultRadius = "20";
    private LocationEntryAdapter locationEntryAdapter;
    private String lastUsedZIP, lastUsedRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.findViewById(R.id.btn_toolbar_home).setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));
        ((TextView) myToolbar.findViewById(R.id.txt_toolbar_title)).setText("Locations");


        SharedPreferences sp = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String zip = sp.getString("ZipCode", "");

        RecyclerView locationView = findViewById(R.id.rv_location);
        locationEntryAdapter = LocationEntryAdapter.getDefaultInstance(new LocationEntryAdapter.ClickListener() {
            @Override
            public void onClick(DonationListEntry entry) {
                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
                intent.putExtra("LOCATION", entry.asJson());
                startActivity(intent);
            }

            @Override
            public void onInfoClick(DonationListEntry entry) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(entry.getInfoURL()));
                startActivity(intent);
            }
        });

        locationView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        locationView.setAdapter(locationEntryAdapter);

        EditText zipInput = findViewById(R.id.et_zip);
        zipInput.setText(zip);
        zipInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                fetchNewLocations(new LocationSearchParams.Builder().PLZ(s.toString()).radius(lastUsedRadius).build());
            }
        });

        EditText radiusInput = findViewById(R.id.et_radius);
        radiusInput.setText(defaultRadius);
        radiusInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                fetchNewLocations(new LocationSearchParams.Builder().PLZ(lastUsedZIP).radius(s.toString()).build());
            }
        });
        fetchNewLocations(new LocationSearchParams.Builder().PLZ(zip).radius(defaultRadius).build());

    }

    private void fetchNewLocations(LocationSearchParams params) {
        if (params == null) return;
        lastUsedRadius = params.getRadius();
        lastUsedZIP = params.getPLZ();
        RemoteLoader.getLocationsAsync(params, result -> runOnUiThread(() -> {
            locationEntryAdapter.submitList(result);
            locationEntryAdapter.notifyDataSetChanged();
        }));
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
                ToolbarActionManager.donorCard(this);
                break;
            case R.id.action_notifications:
                ToolbarActionManager.notifications(this);
                break;
            case R.id.action_share:
                ToolbarActionManager.share(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
