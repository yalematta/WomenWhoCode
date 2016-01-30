package com.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by zassmin on 11/9/15.
 */
@ParseClassName("FeatureTag")
public class FeatureTag extends ParseObject {
    public static final String TAG_KEY = "tag";
    private static final String FEATURE_KEY = "feature";

    public Feature getFeature() {
        Feature feature = null;
        try {
            feature = getParseObject(FEATURE_KEY).fetchIfNeeded();
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return feature;
    }

    public void setFeature(Feature feature) {
        put(FEATURE_KEY, feature);
    }

    public Tag getTag() {
        Tag tag = null;
        try {
            tag = getParseObject(TAG_KEY).fetchIfNeeded();
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return tag;
    }

    public void setTag(Tag tag) {
        put(TAG_KEY, tag);
    }
}

