package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.example.womenwhocode.womenwhocode.models.User;
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
        TextView tvEventSubscribeIcon;
    }

    public EventsAdapter(Context context, ArrayList<Event> events) {
        super(context, android.R.layout.simple_list_item_1, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Event event = getItem(position);
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
            viewHolder.tvEventSubscribeIcon = (TextView) convertView.findViewById(R.id.tvSubscribeIcon);
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

        // onClickSubscribeListener
        viewHolder.tvEventSubscribeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvEventSubscribeIcon = (TextView) v.findViewById(R.id.tvSubscribeIcon);
                // if it's not subscribed - subscribe and do ++
                // could make a parse user for fun right now? -> try to do it without a parse user
                User currentUser = null;
                if (Subscribe.isSubscribed(currentUser, event)) {
                    Subscribe.unSubscribeUserToEvent(currentUser, event);
                    tvEventSubscribeIcon.setText("+");
                } else {
                    Subscribe.subscribeUserToEvent(currentUser, event);
                    tvEventSubscribeIcon.setText("++");
                }
            }
        });

        return convertView;
    }
}
