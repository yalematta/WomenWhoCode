package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by zassmin on 11/9/15.
 */
@ParseClassName("FeatureTag")
public class FeatureTag extends ParseObject {
    public static String FEATURE_KEY = "feature";
    public static String TAG_KEY = "tag";

    public void setFeature(Feature feature) {
        put(FEATURE_KEY, feature);
    }

    public Feature getFeature() {
        Feature feature = null;
        try {
            feature = getParseObject(FEATURE_KEY).fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException nullError) {
            nullError.printStackTrace();
        }
        return feature;
    }

    public void setTag(Tag tag) {
        put(TAG_KEY, tag);
    }

    public Tag getTag() {
        Tag tag = null;
        try {
            tag = getParseObject(TAG_KEY).fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException nullError) {
            nullError.printStackTrace();
        }
        return tag;
    }
}

