package ch.versusvirus.reddrop.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
            //TODO: select timeslot
        });
        barChart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        barChart.setAdapter(timeBarAdapter);

        try {
            ArrayList<AppointmentTimeslot> mockList = new ArrayList<>();
            SimpleDateFormat format = new SimpleDateFormat("hh:mm", Locale.GERMANY);
            mockList.add(new AppointmentTimeslot(format.parse("08:00"), 5));
            mockList.add(new AppointmentTimeslot(format.parse("08:30"), 6));
            mockList.add(new AppointmentTimeslot(format.parse("09:00"), 0));
            mockList.add(new AppointmentTimeslot(format.parse("09:30"), 15));
            mockList.add(new AppointmentTimeslot(format.parse("10:00"), 10));
            mockList.add(new AppointmentTimeslot(format.parse("10:30"), 15));
            mockList.add(new AppointmentTimeslot(format.parse("11:00"), 10));
            mockList.add(new AppointmentTimeslot(format.parse("11:30"), 1));
            mockList.add(new AppointmentTimeslot(format.parse("12:00"), 2));
            timeBarAdapter.submitList(mockList);
            timeBarAdapter.notifyDataSetChanged();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
