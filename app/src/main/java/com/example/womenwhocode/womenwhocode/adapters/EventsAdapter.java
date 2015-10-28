package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
        TextView tvEventSubscribeIcon;
    }

    Event event;
    ParseUser currentUser;
    ParseQuery<Subscribe> subscribeParseQuery;
    ProgressBar pb;
    RelativeLayout rlEvent;
    private static String PARSE_NO_RESULTS_ERROR = "no results found";
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
            viewHolder.tvEventSubscribeIcon = (TextView) convertView.findViewById(R.id.tvSubscribeIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set progress bar
        pb = (ProgressBar) convertView.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        // hide scroll view so the progress bar is the center of attention
        rlEvent = (RelativeLayout) convertView.findViewById(R.id.rlEvent);
        rlEvent.setVisibility(ScrollView.INVISIBLE);

        // start getting subscribed data - will remove this
        subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
        subscribeParseQuery.whereEqualTo(Subscribe.USER_KEY, currentUser);
        subscribeParseQuery.whereEqualTo(Subscribe.EVENT_KEY, event);
        subscribeParseQuery.getFirstInBackground(new GetCallback<Subscribe>() {
            @Override
            public void done(Subscribe subscribe, ParseException e) {
                if (e == null && subscribe != null) {
                    Log.d("SUB_WIN", subscribe.toString());
                    if (subscribe.getSubscribed() == true) {
                        viewHolder.tvEventSubscribeIcon.setText("++");
                    } else {
                        viewHolder.tvEventSubscribeIcon.setText("+");
                    }
                    // set remaining data
                    viewHolder.tvEventTitle.setText(event.getTitle());
                    viewHolder.tvEventLocation.setText(event.getLocation());
                    viewHolder.tvEventDate.setText(event.getEventDateTime());

                    // hide progress spinner
                    pb.setVisibility(ProgressBar.GONE);
                    rlEvent.setVisibility(ListView.VISIBLE);
                } else {
                    // event and user have no subscription
                    viewHolder.tvEventSubscribeIcon.setText("+");
                    viewHolder.tvEventTitle.setText(event.getTitle());
                    viewHolder.tvEventLocation.setText(event.getLocation());
                    viewHolder.tvEventDate.setText(event.getEventDateTime());

                    // hide progress spinner
                    pb.setVisibility(ProgressBar.GONE);
                    rlEvent.setVisibility(ListView.VISIBLE);
                }
            }
        });

        // onClickSubscribeListener
        viewHolder.tvEventSubscribeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                subscribeParseQuery = ParseQuery.getQuery(Subscribe.class);
                subscribeParseQuery.whereEqualTo(Subscribe.USER_KEY, currentUser);
                subscribeParseQuery.whereEqualTo(Subscribe.EVENT_KEY, event);
                subscribeParseQuery.getFirstInBackground(new GetCallback<Subscribe>() {
                    @Override
                    public void done(Subscribe sub, ParseException e) {
                        // determine whether to subscribe or unsubscribe the user
                        // TODO: add animation here for delay
                        if (sub != null) {
                            // subForEventAndUser = instead of query
                            if (sub.getSubscribed() == true) { // maybe just check against icon value
                                sub.setSubscribed(false);
                                sub.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        viewHolder.tvEventSubscribeIcon.setText("+");
                                    }
                                });
                            } else {
                                sub.setSubscribed(true);
                                sub.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        viewHolder.tvEventSubscribeIcon.setText("++");
                                    }
                                });
                            }
                        } else {
                            // create subscription - stays the same
                            Subscribe subscribe = new Subscribe();
                            subscribe.setSubscribed(true);
                            subscribe.setUser(currentUser);
                            subscribe.setEvent(event);
                            subscribe.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    viewHolder.tvEventSubscribeIcon.setText("++");
                                }
                            });
                        }
                    }
                });
            }
        });

        return convertView;
    }
}
