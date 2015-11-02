package com.example.womenwhocode.womenwhocode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Message;
import com.example.womenwhocode.womenwhocode.utils.CircleTransform;
import com.example.womenwhocode.womenwhocode.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by zassmin on 11/1/15.
 */
public class ChatListAdapter extends ArrayAdapter<Message> {
    private String mUserId;

    public ChatListAdapter(Context context, String userId, ArrayList<Message> messages) {
        super(context, 0, messages);
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_chat, parent, false);

            final ViewHolder holder = new ViewHolder();
            // current user message stuff
            holder.rlCurrentUser = (RelativeLayout) convertView.findViewById(R.id.rlCurrentUser);
            holder.tvBody = (TextView)convertView.findViewById(R.id.tvBody);
            holder.ivCurrentUserProfile = (ImageView)convertView.findViewById(R.id.ivCurrentUserProfile);
            holder.tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
            holder.tvFullName = (TextView) convertView.findViewById(R.id.tvFullName);

            // other users message stuff
            holder.rlOtherUser = (RelativeLayout) convertView.findViewById(R.id.rlOtherUser);
            holder.ivOtherProfileLeft = (ImageView)convertView.findViewById(R.id.ivOtherProfileLeft);
            holder.tvOtherBody = (TextView) convertView.findViewById(R.id.tvOtherBody);
            holder.tvOtherCreatedAt = (TextView) convertView.findViewById(R.id.tvOtherCreatedAt);
            holder.tvOtherFullName = (TextView) convertView.findViewById(R.id.tvOtherFullName);

            convertView.setTag(holder);
        }
        final Message message = getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        final boolean isMe = message.getUserId().equals(mUserId);
        String date = Utilities.dateTimeParser(message.getCreatedAt().getTime(), Utilities.TIME_FORMAT);
        // decide which rl to render
        if (isMe) {
            // make visible one RL rlCurrentUser
            holder.rlCurrentUser.setVisibility(View.VISIBLE);
            holder.rlOtherUser.setVisibility(View.GONE);

            // set views
            holder.ivCurrentUserProfile.setImageResource(R.mipmap.ic_wwc_launcher);
            holder.tvBody.setText(message.getBody());
            holder.tvCreatedAt.setText(date);
            if (message.getProfile() != null) {
                if (message.getProfile().getPhotoFile() != null) {
                    Picasso.with(getContext())
                            .load(message.getProfile().getPhotoFile().toString())
                            .transform(new CircleTransform())
                            .resize(50, 50)
                            .centerCrop()
                            .into(holder.ivCurrentUserProfile);
                }
                if (message.getProfile().getFullName() != null) {
                    holder.tvFullName.setText(message.getProfile().getFullName());
                }
            }
        } else {
            // make visible the other RL rlOtherUser
            holder.rlOtherUser.setVisibility(View.VISIBLE);
            holder.rlCurrentUser.setVisibility(View.GONE);

            // set views
            holder.ivOtherProfileLeft.setImageResource(R.mipmap.ic_wwc);
            holder.tvOtherBody.setText(message.getBody());
            holder.tvOtherCreatedAt.setText(date);
            if (message.getProfile() != null) {
                if (message.getProfile().getPhotoFile() != null) {
                    Picasso.with(getContext())
                            .load(message.getProfile().getPhotoFile().toString())
                            .transform(new CircleTransform())
                            .resize(50, 50)
                            .centerCrop()
                            .into(holder.ivOtherProfileLeft);
                }
                if (message.getProfile().getFullName() != null) {
                    holder.tvOtherFullName.setText(message.getProfile().getFullName());
                }
            }
        }

        return convertView;
    }

    final class ViewHolder {
        // current user message view
        public RelativeLayout rlCurrentUser;
        public ImageView ivCurrentUserProfile;
        public TextView tvFullName;
        public TextView tvCreatedAt;
        public TextView tvBody;

        // other users message view
        public RelativeLayout rlOtherUser;
        public ImageView ivOtherProfileLeft;
        public TextView tvOtherFullName;
        public TextView tvOtherCreatedAt;
        public TextView tvOtherBody;

    }
}
