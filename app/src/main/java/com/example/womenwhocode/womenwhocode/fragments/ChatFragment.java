package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.womenwhocode.womenwhocode.R;

/**
 * Created by zassmin on 10/28/15.
 */
public class ChatFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        return view;
    }

    // TODO: render each chat message through the item_chat xml
    // TODO: create an adapter for the item chat
}
