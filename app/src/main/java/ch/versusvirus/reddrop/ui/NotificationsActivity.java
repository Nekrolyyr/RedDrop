package ch.versusvirus.reddrop.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.versusvirus.reddrop.R;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar myToolbar = findViewById(R.id.my_toolbar_notification);
        setSupportActionBar(myToolbar);
        setTitle("NotificationsActivity");

        List<String[]> notificationString = new ArrayList<>();
        notificationString.add(new String[]{"Scheduled Donation", "01.01.2021"});

        RecyclerView notifications = findViewById(R.id.rv_notifications);
        notifications.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        notifications.setAdapter(new NotificationAdapter());
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
}
