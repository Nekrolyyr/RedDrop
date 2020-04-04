package ch.versusvirus.reddrop.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.model.LocationSearchParams;
import ch.versusvirus.reddrop.ui.adapter.LocationEntryAdapter;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        RecyclerView locationView = findViewById(R.id.rv_location);
        LocationEntryAdapter adapter = LocationEntryAdapter.getDefaultInstance(client -> {
            //TODO: open next activity
        });
        locationView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        locationView.setAdapter(adapter);
        RemoteLoader.getLocationsAsync(new LocationSearchParams.Builder().PLZ("8645").radius("100").build(), result -> {
            runOnUiThread(() -> {
                adapter.submitList(result);
                adapter.notifyDataSetChanged();
            });
        });
    }
}
