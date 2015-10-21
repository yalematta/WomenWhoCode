package com.example.womenwhocode.womenwhocode.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Subscribe;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class EventDetailsActivity extends AppCompatActivity {
    TextView tvEventTitle;
    TextView tvEventDate;
    TextView tvEventVenue;
    TextView tvEventUrl;
    TextView tvEventDescription;
    TextView tvAwesomeCount;
    TextView tvSubscribeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        setUpView();

    }

    private void setUpView() {
        // look up views
        tvEventTitle = (TextView) findViewById(R.id.tvEventTitle);
        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        // TextView tvEventTime = (TextView) findViewById(R.id.tvEventTime);
        tvEventVenue = (TextView) findViewById(R.id.tvEventVenue);
        tvEventUrl = (TextView) findViewById(R.id.tvEventUrl);
        tvEventDescription = (TextView) findViewById(R.id.tvEventDescription);
        tvAwesomeCount = (TextView) findViewById(R.id.tvAwesomeCount);
        tvSubscribeCount = (TextView) findViewById(R.id.tvSubscribeCount);

        // get event from intent
        String event_id = getIntent().getStringExtra("event_id");

        // query parse
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        // First try to find from the cache and only then go to network
        // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(event_id, new GetCallback<Event>() {
            public void done(Event event, ParseException e) {
                if (e == null) {
                    if (event != null) {
                        setEventData(event);
                    } else {
                        Log.d("EVENT_PS_NO_DATA", e.toString());
                    }

                } else {
                    Log.d("EVENT_PS_ERROR", e.toString());
                }
            }
        });
    }

    private void setEventData(Event event) {
        // setup views
        tvEventTitle.setText(event.getTitle());
        tvEventDate.setText(Event.getDateTime(event.getEventDateTime()));
        // tvEventTime.setText(event.getDateTime(event.getEventDateTime()));
        tvEventVenue.setText(event.getLocation());
        tvEventUrl.setText(event.getUrl());
        tvEventDescription.setText(Html.fromHtml(event.getDescription()));
        tvAwesomeCount.setText(String.valueOf(event.getAwesomeCount()));
        int subscribeCount = 0;
        try {
            subscribeCount = Subscribe.getCountFor(event);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvSubscribeCount.setText(String.valueOf(subscribeCount + " Subscribed"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
