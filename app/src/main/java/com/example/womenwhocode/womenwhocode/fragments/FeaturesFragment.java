package com.example.womenwhocode.womenwhocode.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class FeaturesFragment extends Fragment {
    private FeaturesAdapter aFeatures;
    private ListView lvFeatures;
    private ProgressBar pb;

    private OnFeatureItemClickListener listener;

    public interface OnFeatureItemClickListener {
        void onFeatureClickListener(Feature feature);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Feature> features = new ArrayList<>();
        aFeatures = new FeaturesAdapter(getActivity(), features);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);

        lvFeatures = (ListView) view.findViewById(R.id.lvFeatures);
        lvFeatures.setVisibility(ListView.INVISIBLE);

        // show progress bar in the meantime
        pb = (ProgressBar) view.findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        lvFeatures.setAdapter(aFeatures);

        populateFeaturesList();

        lvFeatures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Feature feature = aFeatures.getItem(position);
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
                    aFeatures.clear();
                    aFeatures.addAll(lFeatures);
                    // hide progress bar, make list view appear
                    pb.setVisibility(ProgressBar.GONE);
                    lvFeatures.setVisibility(ListView.VISIBLE);
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