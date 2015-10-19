package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.parse.ParseException;

import java.util.ArrayList;

/**
 * Created by zassmin on 10/18/15.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    private static class ViewHolder {
        TextView tvEventTitle;
        TextView tvEventLocation;
        TextView tvSubscribeCount;
        TextView tvEventTime;
        TextView tvEventDate;
    }

    public EventsAdapter(Context context, ArrayList<Event> events) {
        super(context, android.R.layout.simple_list_item_1, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);
        int eventSubscribe = 0;
        try {
              eventSubscribe = Subscribe.getCountFor(event);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_event, parent, false);
            viewHolder.tvEventTitle = (TextView) convertView.findViewById(R.id.tvEventTitle);
            viewHolder.tvEventLocation = (TextView) convertView.findViewById(R.id.tvEventLocation);
            viewHolder.tvSubscribeCount = (TextView) convertView.findViewById(R.id.tvSubscribeCount);
            // viewHolder.tvEventTime = (TextView) convertView.findViewById(R.id.tvEventTime);
            // TODO: decide layout for time!
            viewHolder.tvEventDate = (TextView) convertView.findViewById(R.id.tvEventDate);
            // TODO: set the subscribe icon (needs true or false switches), until then display default
            // TextView tvEventSubscribeIcon = (TextView) convertView.findViewById(R.id.tvSubscribeIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // populate data
        viewHolder.tvEventTitle.setText(event.getTitle());
        viewHolder.tvSubscribeCount.setText(String.valueOf(eventSubscribe + " subscribers"));
        viewHolder.tvEventLocation.setText(event.getLocation());
        String prettyDateTime = Event.getDateTime(event.getEventDateTime());
        viewHolder.tvEventDate.setText(prettyDateTime);

        return convertView;
    }
}
