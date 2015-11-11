package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.utils.Utilities;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zassmin on 10/18/15.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private final List<Event> mEvents;
    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        EventsAdapter.listener = listener;
    }

    public EventsAdapter(List<Event> events) {
        this.mEvents = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventView = inflater.inflate(R.layout.item_event, parent, false);
        return new ViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mEvents.get(position);

        TextView tvEventNetwork = holder.tvEventNetwork;
        TextView tvEventTitle = holder.tvEventTopicTitle;
        TextView tvEventLocation = holder.tvEventLocation;
        TextView tvEventTime = holder.tvEventTime;
        TextView tvEventDate = holder.tvEventDate;

        if (event.getNetwork() != null) {
            tvEventNetwork.setText(event.getNetwork().getTitle() + " network");
        }

        long time = Utilities.setLocalDateTime(event.getEventDateTime());
        String date = Utilities.dateTimeParser(time, Utilities.DATE_FORMAT);
        String prettyTime = Utilities.dateTimeParser(time, Utilities.TIME_FORMAT);

        tvEventTitle.setText(event.getTitle());
        tvEventLocation.setText("at " + event.getLocation());
        tvEventTime.setText(prettyTime);
        tvEventDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvEventTopicTitle) public TextView tvEventTopicTitle;
        @Bind(R.id.tvEventLocation) public TextView tvEventLocation;
        @Bind(R.id.tvEventTime) public TextView tvEventTime;
        @Bind(R.id.tvEventDate) public TextView tvEventDate;
        @Bind(R.id.tvEventNetwork) public TextView tvEventNetwork;

        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }

    }
}