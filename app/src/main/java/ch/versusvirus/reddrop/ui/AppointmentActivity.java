package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.data_access.RemoteLoader;
import ch.versusvirus.reddrop.logic.AppointmentScheduler;
import ch.versusvirus.reddrop.logic.model.Appointment;
import ch.versusvirus.reddrop.logic.model.AppointmentGetterParams;
import ch.versusvirus.reddrop.logic.model.DonationListEntry;
import ch.versusvirus.reddrop.ui.adapter.TimeBarAdapter;

public class AppointmentActivity extends AppCompatActivity {

    TimeBarAdapter timeBarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.findViewById(R.id.btn_toolbar_home).setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));

        DonationListEntry location = DonationListEntry.fromJson(getIntent().getStringExtra("LOCATION"));

        RecyclerView barChart = findViewById(R.id.rv_barChart);
        timeBarAdapter = TimeBarAdapter.getDefaultInstance(client -> {
            //TODO: select timeslot
        });
        barChart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        barChart.setAdapter(timeBarAdapter);

        TextView date = findViewById(R.id.txt_date);
        date.setText(location.getWeekday() + ", " + location.getDate());
        TextView organizer = findViewById(R.id.txt_organizer);
        organizer.setText(location.getAdditionalInfo());
        TextView time = findViewById(R.id.txt_time);
        time.setText(location.getTimeStart() + " - " + location.getTimeEnd());

        ((TextView) myToolbar.findViewById(R.id.txt_toolbar_title)).setText(location.getVillageInfo());

        RemoteLoader.getAppointmentStatusAsync(new AppointmentGetterParams.Builder().id(location.getId()).nSlots(extractTimeFrames(location)).build(), result -> {
            runOnUiThread(() -> {
                timeBarAdapter.setMaxExpectedPeople(result.getMaxCapacityPerSlot());
                timeBarAdapter.submitList(result.getTimeslots(location.getTimeStart()));
                timeBarAdapter.notifyDataSetChanged();
                Spinner timeFrameSelector = findViewById(R.id.spinner_timeframe);
                SpinnerAdapter adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, result.getTimeslots(location.getTimeStart()));
                timeFrameSelector.setAdapter(adapter);
            });
        });

        Button schedule = findViewById(R.id.btn_schedule);
        schedule.setOnClickListener(v -> {
            Spinner timeFrameSelector = findViewById(R.id.spinner_timeframe);
            AppointmentScheduler.scheduleAt(location, timeFrameSelector.getSelectedItemPosition(), getApplicationContext());
            startActivity(new Intent(this, HomeActivity.class));
        });
    }

    private int extractTimeFrames(DonationListEntry location) {
        try {
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(location.getTimeStart()));
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(location.getTimeEnd()));
            int hours = endTime.get(Calendar.HOUR_OF_DAY) - startTime.get(Calendar.HOUR_OF_DAY);
            int slotno = (hours * 60 - startTime.get(Calendar.MINUTE) + endTime.get(Calendar.MINUTE)) / Appointment.slotLengthMin;
            return slotno;
        } catch (ParseException e) {
            return 0;
        }
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
                startActivity(new Intent(this, MyCardActivity.class));
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
