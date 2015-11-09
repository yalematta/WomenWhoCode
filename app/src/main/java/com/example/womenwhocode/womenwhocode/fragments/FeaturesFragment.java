package com.example.womenwhocode.womenwhocode.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.FeaturesAdapter;
import com.example.womenwhocode.womenwhocode.models.Feature;
import com.example.womenwhocode.womenwhocode.utils.LocalDataStore;
import com.example.womenwhocode.womenwhocode.utils.NetworkConnectivityReceiver;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class FeaturesFragment extends Fragment {
    private FeaturesAdapter aFeatures;
    private RecyclerView rvFeatures;
    private ProgressBar pb;
    private ArrayList<Feature> features;
    private OnFeatureItemClickListener listener;

    public interface OnFeatureItemClickListener {
        void onFeatureClickListener(Feature feature);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        features = new ArrayList<>();
        aFeatures = new FeaturesAdapter(features);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);

        rvFeatures = (RecyclerView) view.findViewById(R.id.lvFeatures);
        rvFeatures.setVisibility(RecyclerView.INVISIBLE);

        // show progress bar in the meantime
        pb = (ProgressBar) view.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        rvFeatures.setAdapter(aFeatures);
        rvFeatures.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFeatures.setItemAnimator(new SlideInUpAnimator()); // FIXME: can't tell if it's working!

        populateFeaturesList();

        aFeatures.setOnItemClickListener(new FeaturesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Feature feature = features.get(position);
                if (feature.getTitle().contains("Recommend")) {
                    showEditDialog();
                } else {
                    listener.onFeatureClickListener(feature);
                }
            }
        });

        return view;
    }

    private void showEditDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        RecommendFeatureDialog recommendFeatureDialog = RecommendFeatureDialog.newInstance();
        recommendFeatureDialog.show(fm, "fagment_dialog_recommend");
    }


    private void populateFeaturesList() {
        ParseQuery<Feature> query = ParseQuery.getQuery(Feature.class);

        if (!NetworkConnectivityReceiver.isNetworkAvailable(getContext())) {
            query.fromPin(LocalDataStore.FEATURES_PIN);
        }

        query.orderByAscending(Feature.TITLE_KEY);
        query.findInBackground(new FindCallback<Feature>() {
            public void done(List<Feature> lFeatures, ParseException e) {
                if (e == null && lFeatures.size() > 0) {
                    features.clear();
                    features.addAll(lFeatures);
                    aFeatures.notifyDataSetChanged(); // FIXME: last resort, do something else
                    // hide progress bar, make list view appear
                    pb.setVisibility(ProgressBar.GONE);
                    rvFeatures.setVisibility(RecyclerView.VISIBLE);
                    LocalDataStore.unpinAndRepin(lFeatures, LocalDataStore.FEATURES_PIN);
                } else {
                    Log.d("Message", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFeatureItemClickListener) {
            listener = (OnFeatureItemClickListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement FeaturesFragment.OnFeatureItemClickListener");
        }
    }
}