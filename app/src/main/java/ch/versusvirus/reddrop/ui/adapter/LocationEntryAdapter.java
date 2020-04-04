package ch.versusvirus.reddrop.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.DonationListEntry;

public class LocationEntryAdapter extends ListAdapter<DonationListEntry, LocationEntryAdapter.ViewHolder> {

    private final ClickListener listener;

    private LocationEntryAdapter(ClickListener listener) {
        super(new DiffUtil.ItemCallback<DonationListEntry>() {
            @Override
            public boolean areItemsTheSame(@NonNull DonationListEntry first, @NonNull DonationListEntry other) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull DonationListEntry first, @NonNull DonationListEntry other) {
                return false;
            }
        });
        this.listener = listener;
    }

    public static LocationEntryAdapter getDefaultInstance(ClickListener listener) {
        return new LocationEntryAdapter(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.location_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        DonationListEntry entry = getItem(position);
        if (entry == null) return;
        holder.bind(entry, listener);
    }

    public interface ClickListener {
        void onClick(DonationListEntry entry);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View view) {
            super(view);
        }

        void bind(DonationListEntry entry, ClickListener listener) {
            TextView text = itemView.findViewById(R.id.txt_entry);
            text.setText(entry.getDate() + " " + entry.getVillageInfo());
            itemView.setOnClickListener(v -> listener.onClick(entry));
        }
    }
}
