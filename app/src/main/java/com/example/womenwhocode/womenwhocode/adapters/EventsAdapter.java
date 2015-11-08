package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.utils.Utilities;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zassmin on 10/18/15.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    Event event;
    ParseUser currentUser;
    ViewHolder holder;

    public EventsAdapter(Context context, ArrayList<Event> events) {
        super(context, android.R.layout.simple_list_item_1, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // set up objects
        event = getItem(position);
        currentUser = ParseUser.getCurrentUser();

        if (convertView == null) {
            holder = new ViewHolder(convertView);
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_event, parent, false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (event.getNetwork() != null) {
            holder.tvEventNetwork.setText(event.getNetwork().getTitle() + " network");
        }

        long time = Utilities.setLocalDateTime(event.getEventDateTime());
        String date = Utilities.dateTimeParser(time, Utilities.DATE_FORMAT);
        String prettyTime = Utilities.dateTimeParser(time, Utilities.TIME_FORMAT);

        holder.tvEventTitle.setText(event.getTitle());
        holder.tvEventLocation.setText("at " + event.getLocation());
        holder.tvEventTime.setText(prettyTime);
        holder.tvEventDate.setText(date);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tvEventTitle)
        TextView tvEventTitle;
        @Bind(R.id.tvEventLocation)
        TextView tvEventLocation;
        @Bind(R.id.tvEventTime)
        TextView tvEventTime;
        @Bind(R.id.tvEventDate)
        TextView tvEventDate;
        @Bind(R.id.tvEventNetwork)
        TextView tvEventNetwork;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
