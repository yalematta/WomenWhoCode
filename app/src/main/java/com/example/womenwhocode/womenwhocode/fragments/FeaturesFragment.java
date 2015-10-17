package com.example.womenwhocode.womenwhocode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.womenwhocode.womenwhocode.R;
import com.example.womenwhocode.womenwhocode.adapters.FeaturesArrayAdapter;
import com.example.womenwhocode.womenwhocode.models.Feature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shehba.shahab on 10/16/15.
 */
public class FeaturesFragment extends Fragment {
    FeaturesArrayAdapter aFeatures;
    ArrayList<Feature> features;
    ListView lvFeatures;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        features = new ArrayList<>();
        aFeatures = new FeaturesArrayAdapter(getActivity(), features);
        populateFeaturesList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);
        lvFeatures = (ListView) view.findViewById(R.id.lvFeatures);
        lvFeatures.setAdapter(aFeatures);
        return view;
    }

    void addAll(List<Feature> features) {
        aFeatures.addAll(features);
    }

    void clearAdapter() {
        aFeatures.clear();
    }

    void populateFeaturesList() {
        clearAdapter();
    }
}