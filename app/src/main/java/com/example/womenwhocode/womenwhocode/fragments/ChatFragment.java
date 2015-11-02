package com.example.womenwhocode.womenwhocode.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.ChatListAdapter;
import com.example.womenwhocode.womenwhocode.models.Message;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by zassmin on 10/28/15.
 */
public class ChatFragment extends Fragment {

    private View view;
    private ListView lvChat;
    private ArrayList<Message> messages;
    private ChatListAdapter aChatList;
    private ProgressBar pb;
    private ParseUser currentUser;
    private RelativeLayout rlChatMessagesFragment;
    private Button btnSend;
    private EditText etMessage;
    private boolean mFirstLoad;
    private Handler handler;
    private Runnable runnable;
    private int RUN_FREQUENCY = 1000; // ms
    private Date recentCreatedAt;

    public static int MAX_CHAT_MESSAGES_TO_SHOW = 50;

    // TODO: use material icon for button, initial is gray and when you start typing make it teal
    // TODO: FeatureChatFragment - feature_id, subscribe_id?
    // TODO: figure out to how get user profile data with less database calls
    // TODO: figure out how to get updated subscribed values, maybe a listener with instance implementation here
    // public interface OnSubscribeStatusChange {
    //     void onSubscribeStatusChange(); // call in EventDetailActivity and FeatureDetailActivity
    // }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = ParseUser.getCurrentUser();
        messages = new ArrayList<>();
        aChatList = new ChatListAdapter(getActivity(), currentUser.getObjectId(), messages);
        handler = new Handler();

        // Defines a runnable which is run every 100ms
        runnable = new Runnable() {
            @Override
            public void run() {
                receiveMessages();
                // FIXME: only do this when there are new messages - handler.postDelayed(this, RUN_FREQUENCY);
                handler.postDelayed(this, RUN_FREQUENCY);
                // query for any message more recent than the most recent
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        lvChat = (ListView) view.findViewById(R.id.lvChat);
        rlChatMessagesFragment = (RelativeLayout) view.findViewById(R.id.rlChatMessagesFragment);

        // setup the view
        setUpView();

        handler.postDelayed(runnable, RUN_FREQUENCY);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        // stop handler
        handler.removeCallbacks(runnable);
    }

    protected void setSpinners() {
        lvChat.setVisibility(ListView.INVISIBLE);
        pb = (ProgressBar) view.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);
    }

    protected void clearSpinners() {
        lvChat.setVisibility(ListView.VISIBLE);
        pb.setVisibility(ProgressBar.GONE);
    }

    protected void addAll(List<Message> messageList) {
        aChatList.addAll(messageList);
    }

    protected void add(List<Message> messageList) {
        for (Message message : messageList) {
            if (message != aChatList.getItem(aChatList.getCount() -1)) { // double post bug
                aChatList.add(message);
            }
        }
    }

    protected void clear() {
        aChatList.clear();
    }

    protected void scrollToBottom() {
        lvChat.setSelection(aChatList.getCount() - 1);
    }

    protected void noMessagesView(String color) { // feature or event color
        int intColor = Color.parseColor(String.valueOf(color));
        rlChatMessagesFragment.setBackgroundColor(intColor);
    }

    protected void notSubscribedView() {
        rlChatMessagesFragment.setBackgroundColor(Color.BLUE); // temp
        // display instead is not subscribed message
    }

    protected void setupMessagePosting(String body, String userId) { // maybe change to interface
        // override this in the other fragments
    }

    // will be called in the handler that listens for new messages
    protected void receiveMessages() { // maybe change to interface
        // override this in the other fragments
    }

    protected boolean isFirstLoad() {
        return mFirstLoad;
    }

    protected void setFirstLoad(boolean load) {
        // Keep track of initial load to scroll to the bottom of the ListView
        this.mFirstLoad = load;
    }

    protected void setMostRecentcreatedAt(Date createAt) {
        this.recentCreatedAt = createAt;
    }

    protected Date getMostRecentcreatedAt() {
        return this.recentCreatedAt;
    }

    private void setUpView() {
        // grab message posting views
        btnSend = (Button) view.findViewById(R.id.btnSend);
        etMessage = (EditText) view.findViewById(R.id.etMessage);

        // Automatically scroll to the bottom when a data set change notification is received
        // and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);

        // FIXME what do to about m boolean
        setFirstLoad(true);
        lvChat.setAdapter(aChatList);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = etMessage.getText().toString();
                if (TextUtils.isEmpty(body)) {
                    return;
                }
                // post message
                setupMessagePosting(body, currentUser.getObjectId());

                etMessage.setText("");
                etMessage.clearFocus();
                btnSend.clearFocus();

                // disable keyboard
                InputMethodManager imm =(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }
}
