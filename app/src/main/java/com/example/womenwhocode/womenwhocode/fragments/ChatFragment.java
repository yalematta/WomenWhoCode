package com.example.womenwhocode.womenwhocode.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.ChatListAdapter;
import com.example.womenwhocode.womenwhocode.models.Message;
import com.example.womenwhocode.womenwhocode.models.Profile;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zassmin on 10/28/15.
 */
public class ChatFragment extends Fragment {

    public static int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private View view;
    private ListView lvChat;
    private ChatListAdapter aChatList;
    private ProgressBar pb;
    private ParseUser currentUser;
    private Button btnSend;
    private EditText etMessage;
    private boolean mFirstLoad;
    private Handler handler;
    private Runnable runnable;
    private final int RUN_FREQUENCY = 1000; // ms
    private Date recentCreatedAt;
    private Profile profile;
    private TextView noChats;

    // TODO: use material icon for button, initial is gray and when you start typing make it teal
    // TODO: FeatureChatFragment - feature_id, subscribe_id?
    // TODO: figure out how to get updated subscribed values, maybe a listener with instance implementation here
    // public interface OnSubscribeStatusChange {
    //     void onSubscribeStatusChange(); // call in EventDetailActivity and FeatureDetailActivity
    // }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = ParseUser.getCurrentUser();
        ArrayList<Message> messages = new ArrayList<>();
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

        ParseQuery<Profile> parseQuery = ParseQuery.getQuery(Profile.class);
        parseQuery.whereEqualTo(Profile.USER_KEY, currentUser);
        parseQuery.getFirstInBackground(new GetCallback<Profile>() {
            @Override
            public void done(Profile p, ParseException e) {
                if (e == null) {
                    profile = p;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        lvChat = (ListView) view.findViewById(R.id.lvChat);
        RelativeLayout rlChatMessagesFragment = (RelativeLayout) view.findViewById(R.id.rlChatMessagesFragment);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // setup the view
        setUpView();

        handler.postDelayed(runnable, RUN_FREQUENCY);
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
            if (message != aChatList.getItem(aChatList.getCount() - 1)) { // double post bug
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

    protected Date getMostRecentcreatedAt() {
        return this.recentCreatedAt;
    }

    protected void setMostRecentcreatedAt(Date createAt) {
        this.recentCreatedAt = createAt;
    }

    protected Profile getUserProfile() {
        return this.profile;
    }

    private void setUpView() {
        // grab message posting views
        btnSend = (Button) view.findViewById(R.id.btnSend);
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        etMessage.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_SEND ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            sendMessage();
                            return true;
                        }
                        return false;
                    }
                });

        // Automatically scroll to the bottom when a data set change notification is received
        // and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvChat.setTranscriptMode(1);

        // FIXME what do to about m boolean
        setFirstLoad(true);
        lvChat.setAdapter(aChatList);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String messageBody = etMessage.getText().toString();
        if (TextUtils.isEmpty(messageBody)) {
            return;
        }
        // post message
        setupMessagePosting(messageBody, currentUser.getObjectId());

        etMessage.setText("");
        etMessage.clearFocus();
        btnSend.clearFocus();

        // disable keyboard
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
