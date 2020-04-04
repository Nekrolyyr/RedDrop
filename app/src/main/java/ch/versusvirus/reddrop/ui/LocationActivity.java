package ch.versusvirus.reddrop.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.model.LocationSearchParams;
import ch.versusvirus.reddrop.logic.model.User;
import ch.versusvirus.reddrop.ui.adapter.LocationEntryAdapter;

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
        RecyclerView locationView = findViewById(R.id.rv_location);
        locationEntryAdapter = LocationEntryAdapter.getDefaultInstance(client -> {
            //TODO: open next activity
        });
        locationView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        locationView.setAdapter(locationEntryAdapter);

        EditText zipInput = findViewById(R.id.et_zip);
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
        fetchNewLocations(new LocationSearchParams.Builder().PLZ(User.getCurrent().getPLZ()).radius(defaultRadius).build());
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
}
