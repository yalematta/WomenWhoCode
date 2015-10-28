package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by zassmin on 10/18/15.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    private static class ViewHolder {
        TextView tvEventTitle;
        TextView tvEventLocation;
        TextView tvEventTime;
        TextView tvEventDate;
    }

    Event event;
    ParseUser currentUser;
    ViewHolder viewHolder;

    public EventsAdapter(Context context, ArrayList<Event> events) {
        super(context, android.R.layout.simple_list_item_1, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // set up objects
        event = getItem(position);
        currentUser = ParseUser.getCurrentUser();

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_event, parent, false);
            viewHolder.tvEventTitle = (TextView) convertView.findViewById(R.id.tvEventTitle);
            viewHolder.tvEventLocation = (TextView) convertView.findViewById(R.id.tvEventLocation);
             viewHolder.tvEventTime = (TextView) convertView.findViewById(R.id.tvEventTime);
            // TODO: decide layout for time!
            viewHolder.tvEventDate = (TextView) convertView.findViewById(R.id.tvEventDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvEventTitle.setText(event.getTitle());
        viewHolder.tvEventLocation.setText(event.getLocation());
        viewHolder.tvEventDate.setText(event.getEventDateTime());

        return convertView;
    }
}
