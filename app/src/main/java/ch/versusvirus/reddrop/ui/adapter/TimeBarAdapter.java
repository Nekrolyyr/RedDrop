package ch.versusvirus.reddrop.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.AppointmentTimeslot;

public class TimeBarAdapter extends ListAdapter<AppointmentTimeslot, TimeBarAdapter.ViewHolder> {

    private final ClickListener listener;
    private int MaxExpectedPeople = 15;

    public int getMaxExpectedPeople() {
        return MaxExpectedPeople;
    }

    public void setMaxExpectedPeople(int maxExpectedPeople) {
        MaxExpectedPeople = maxExpectedPeople;
    }

    private TimeBarAdapter(ClickListener listener) {
        super(new DiffUtil.ItemCallback<AppointmentTimeslot>() {
            @Override
            public boolean areItemsTheSame(@NonNull AppointmentTimeslot first, @NonNull AppointmentTimeslot other) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull AppointmentTimeslot first, @NonNull AppointmentTimeslot other) {
                return false;
            }
        });
        this.listener = listener;
    }

    public static TimeBarAdapter getDefaultInstance(ClickListener listener) {
        return new TimeBarAdapter(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_bar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        AppointmentTimeslot item = getItem(position);
        if (item == null) return;
        holder.bind(item, listener);
    }

    public interface ClickListener {
        void onClick(AppointmentTimeslot item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View view) {
            super(view);
        }

        void bind(AppointmentTimeslot item, ClickListener listener) {
            TextView time = itemView.findViewById(R.id.txt_time);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            time.setText(format.format(item.getTime()));
            View bar = itemView.findViewById(R.id.v_bar);
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) bar.getLayoutParams();
            float bar_percent = (float) (Math.min((float) item.getExpectedPeople() / (float) MaxExpectedPeople, 1) - 0.08);
            lp.matchConstraintPercentHeight = bar_percent;
            bar.setLayoutParams(lp);
            itemView.setOnClickListener(v -> listener.onClick(item));
            TextView expected = itemView.findViewById(R.id.txt_expected);
            if (bar_percent > 0.15) {
                expected.setText(item.getExpectedPeople() + "");
            } else {
                expected.setVisibility(View.INVISIBLE);
            }
        }
    }
}
