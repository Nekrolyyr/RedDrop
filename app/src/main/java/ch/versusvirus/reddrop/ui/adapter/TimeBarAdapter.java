package ch.versusvirus.reddrop.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.AppointmentTimeslot;

public class TimeBarAdapter extends ListAdapter<AppointmentTimeslot, TimeBarAdapter.ViewHolder> {

    private final ClickListener listener;
    private int MaxExpectedPeople = 10;

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
    public void submitList(@Nullable List<AppointmentTimeslot> list) {
        super.submitList(list);
        if (list == null) return;
        for (AppointmentTimeslot slot : list) {
            if (slot.getExpectedPeople() > MaxExpectedPeople) {
                MaxExpectedPeople = (slot.getExpectedPeople() / 5) * 5 + 1;
            }
        }
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
            SimpleDateFormat format = new SimpleDateFormat("hh:mm", Locale.GERMANY);
            time.setText(format.format(item.getTime()));
            ConstraintLayout mConstrainLayout = itemView.findViewById(R.id.cl_holder);
            View bar = itemView.findViewById(R.id.v_bar);
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) bar.getLayoutParams();
            lp.matchConstraintPercentHeight = (float) item.getExpectedPeople() / (float) MaxExpectedPeople;
            itemView.setLayoutParams(lp);
            itemView.setOnClickListener(v -> listener.onClick(item));
        }
    }
}
