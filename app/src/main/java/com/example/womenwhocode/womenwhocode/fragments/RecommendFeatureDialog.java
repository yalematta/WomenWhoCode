package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.womenwhocode.womenwhocode.R;

/**
 * Created by zassmin on 11/4/15.
 */
public class RecommendFeatureDialog extends DialogFragment implements EditText.OnEditorActionListener {
    private EditText mEditText;

    public interface RecommendFeatureDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public RecommendFeatureDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static RecommendFeatureDialog newInstance() {
        RecommendFeatureDialog frag = new RecommendFeatureDialog();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_recommend, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etRecommendation);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            RecommendFeatureDialogListener listener = (RecommendFeatureDialogListener) getActivity();
            listener.onFinishEditDialog("");
            dismiss(); // FIXME: this takes me away from the app...
            return true;
        }
        return false;
    }
}
