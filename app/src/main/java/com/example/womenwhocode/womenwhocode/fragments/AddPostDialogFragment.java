package com.example.womenwhocode.womenwhocode.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.models.Post;
import com.parse.ParseUser;

/**
 * Created by zassmin on 11/12/15.
 */
public class AddPostDialogFragment extends DialogFragment {
    private EditText mEditText;
    private static String TITLE_KEY = "title";
    private OnSubmitPostListener listener;

    public interface OnSubmitPostListener {
        void onSubmitPostListener(String inputText);
    }


    public AddPostDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddPostDialogFragment newInstance(String dialogTitle) {
        AddPostDialogFragment frag = new AddPostDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", dialogTitle);
        frag.setArguments(args);
        return frag;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_post, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get views
        Button btnAddPost = (Button) view.findViewById(R.id.btnAddPost);
        ImageButton ibPostClose = (ImageButton) view.findViewById(R.id.ibPostClose);
        mEditText = (EditText) view.findViewById(R.id.etAddPost);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString(TITLE_KEY, "Add Post");
        getDialog().setTitle(title);

        ibPostClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mEditText.setShowSoftInputOnFocus(true);
        }
        // Show soft keyboard automatically and request focus to field
//        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: support media uploads!
                String postBody = mEditText.getText().toString();
                if (TextUtils.isEmpty(postBody)) {
                    return;
                }

                listener = (OnSubmitPostListener) getActivity();
                listener.onSubmitPostListener(postBody);

                dismiss();

                // close this fragment
            }
        });
    }
}
