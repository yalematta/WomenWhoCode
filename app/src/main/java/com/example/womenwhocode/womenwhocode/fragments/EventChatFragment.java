package com.example.womenwhocode.womenwhocode.fragments;


import android.os.Bundle;
import android.util.Log;

import com.example.womenwhocode.womenwhocode.models.Event;
import com.example.womenwhocode.womenwhocode.models.Message;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by zassmin on 11/1/15.
 */
public class EventChatFragment extends ChatFragment {
    public static String EVENT_ID = "event_id";
    public static String eventId;
    protected ParseQuery<Event> eventParseQuery;
    protected ParseQuery<Message> messageParseQuery;

    public static EventChatFragment newInstance(String eventObjectId) {
        EventChatFragment eventChatFragment = new EventChatFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_ID, eventObjectId);
        eventChatFragment.setArguments(args);
        return eventChatFragment;
    }

    @Override
    protected void setupMessagePosting(String body, String userId) {
        eventId = getArguments().getString(EVENT_ID, "");

        Message message = new Message();
        message.setUserId(userId);
        message.setBody(body);
        if (getUserProfile() != null) {
            message.setProfile(getUserProfile());
        }
        message.setEventId(eventId); // FIXME: what do to if its null or empty
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    receiveMessages();
                } else {
                    Log.d("CHAT_POST_ERROR", e.toString());
                }
            }
        });
    }

    @Override
    protected void receiveMessages() {
        if (isFirstLoad()) {
            setSpinners();
        }

        eventId = getArguments().getString(EVENT_ID, "");

        eventParseQuery = ParseQuery.getQuery(Event.class);
        messageParseQuery = ParseQuery.getQuery(Message.class);
        if (!NetworkConnectivityReceiver.isNetworkAvailable(getContext())) {
            messageParseQuery.fromPin(eventId + LocalDataStore.MESSAGE_PIN);
        }

        // TODO: only query if the user is subscribed

        messageParseQuery.whereEqualTo(Message.EVENT_ID_KEY, eventId);
        if (!isFirstLoad()) {
            messageParseQuery.whereGreaterThan(Message.CREATED_AT_KEY, getMostRecentcreatedAt());
        }

        messageParseQuery.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        messageParseQuery.orderByAscending(Message.CREATED_AT_KEY);

        // TODO: maybe include profile table
        messageParseQuery.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> list, ParseException e) {
                if (e == null && list.size() > 0) {
                    // assuming 0 position is most recent
                    setMostRecentcreatedAt(list.get(list.size()-1).getCreatedAt());

                    // pin locally
                    LocalDataStore.unpinAndRepin(list, eventId + LocalDataStore.MESSAGE_PIN);

                    // Scroll to the bottom of the list on initial load
                    if (isFirstLoad()) {
                        // clear adapter
                        clear();
                        // add to adapter
                        addAll(list);
                        // other things
                        scrollToBottom();
                        setFirstLoad(false);
                        clearSpinners();
                    } else {
                        add(list);
                        scrollToBottom();
                    }

                } else if (e != null) {
                    Log.d("PARSE_EVENTS_POST_FAIL", "Error: " + e.getMessage());
                } else {
                    clearSpinners();
                }
            }
        });
    }
}
