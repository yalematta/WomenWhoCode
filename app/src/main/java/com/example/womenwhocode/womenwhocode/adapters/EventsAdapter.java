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

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }

        TextView tvEventTitle = (TextView) convertView.findViewById(R.id.tvEventTitle);
        TextView tvEventLocation = (TextView) convertView.findViewById(R.id.tvEventLocation);
        // TODO: add \o/ icon next to count!!!!
        TextView tvSubscribeCount = (TextView) convertView.findViewById(R.id.tvSubscribeCount);
        // TODO: display time in a really basic way for now...
        TextView tvEventTime = (TextView) convertView.findViewById(R.id.tvEventTime);
        TextView tvEventDate = (TextView) convertView.findViewById(R.id.tvEventDate);
        // TODO: set the subscribe icon (needs true or false switches), until then display default
        // TextView tvEventSubscribeIcon = (TextView) convertView.findViewById(R.id.tvSubscribeIcon);

        tvEventTitle.setText(event.getTitle());
        tvSubscribeCount.setText(String.valueOf(eventSubscribe + " subscribers"));
        tvEventLocation.setText(event.getLocation());
        String prettyTime = Event.getDateTime(event.getEventDateTime());
        tvEventDate.setText(prettyTime);

        // TODO: render adapter in the event fragment!

        return convertView;
    }
}
