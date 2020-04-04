package ch.versusvirus.reddrop.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.util.ArrayList;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.AppointmentTimeslot;
import ch.versusvirus.reddrop.ui.adapter.TimeBarAdapter;

public class AppointmentActivity extends AppCompatActivity {

    TimeBarAdapter timeBarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        RecyclerView barChart = findViewById(R.id.rv_barChart);
        timeBarAdapter = TimeBarAdapter.getDefaultInstance(client -> {
            //TODO: open next activity
        });
        barChart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        barChart.setAdapter(timeBarAdapter);

        ArrayList<AppointmentTimeslot> mockList = new ArrayList<>();
        mockList.add(new AppointmentTimeslot(new Time((8 * 60 + 15 * 60) * 1000), 5));
        mockList.add(new AppointmentTimeslot(new Time((8 * 60 + 45 * 60) * 1000), 6));
        mockList.add(new AppointmentTimeslot(new Time((9 * 60 + 15 * 60) * 1000), 7));
        mockList.add(new AppointmentTimeslot(new Time((10 * 60 + 15 * 60) * 1000), 16));
        mockList.add(new AppointmentTimeslot(new Time((12 * 60 + 0 * 60) * 1000), 10));
        timeBarAdapter.submitList(mockList);
        timeBarAdapter.notifyDataSetChanged();
    }
}
