package ch.versusvirus.reddrop.ui;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.Reminder;

public class NotificationsActivity extends AppCompatActivity {

    private Reminder reminder;

    private List<String[]> notificationString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.findViewById(R.id.btn_toolbar_home).setOnClickListener(v -> startActivity(new Intent(this, WelcomeActivity.class)));

        notificationString.add(new String[]{"Scheduled Donation", "01.01.2021"});
        notificationString.add(new String[]{"Scheduled Donation", "01.04.2021"});

        RecyclerView notifications = findViewById(R.id.rv_notifications);
        notifications.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        NotificationAdapter adapter = new NotificationAdapter();
        notifications.setAdapter(adapter);

        adapter.submitList(notificationString);

        reminder = new Reminder(getApplicationContext());
        findViewById(R.id.btn_notification_test).setOnClickListener(v -> {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            String title = "Blood Donation Request";
            String content = "URGENT: Any blood from people with Covid-19 needed.";
            notificationString.add(new String[]{title, content});
            adapter.submitList(notificationString);
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(this, HomeActivity.class);
            intent = intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Notification notification = reminder.specialNotification(title, content, intent);
            notificationManager.notify(1, notification);
        });
    }

    class NotificationAdapter extends ListAdapter<String[], NotificationAdapter.NotificationViewHolder> {

        protected NotificationAdapter() {
            super(new DiffUtil.ItemCallback<String[]>() {

                @Override
                public boolean areItemsTheSame(@NonNull String[] oldItem, @NonNull String[] newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull String[] oldItem, @NonNull String[] newItem) {
                    return false;
                }
            });
        }

        class NotificationViewHolder extends RecyclerView.ViewHolder {

            NotificationViewHolder(View view) {
                super(view);
            }

            void bind(String[] entry) {
                TextView title = itemView.findViewById(R.id.txt_notification_title);
                TextView date = itemView.findViewById(R.id.txt_notification_date);

                title.setText(entry[0]);
                date.setText(entry[1]);
            }
        }

        @NonNull
        @Override
        public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.notification_entry, parent, false);
            return new NotificationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
            String[] entry = getItem(position);
            if (entry == null) return;
            holder.bind(entry);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
