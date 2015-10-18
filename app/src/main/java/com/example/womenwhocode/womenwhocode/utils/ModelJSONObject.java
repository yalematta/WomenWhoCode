package com.example.womenwhocode.womenwhocode.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zassmin on 10/17/15.
 */
public class ModelJSONObject extends JSONObject {
    public ModelJSONObject(JSONObject parent) throws JSONException {
        super(parent.toString());
    }

    @Override
    public boolean getBoolean(String name) throws JSONException {
        try {
            return super.getBoolean(name);
        } catch (JSONException e) {
            if (e.getMessage().contains("No value for")) {
                return false;
            } else {
                throw e;
            }
        }
    }

    @Override
    public String getString(String name) throws JSONException {
        try {
            return super.getString(name);
        } catch (JSONException e) {
            if (e.getMessage().contains("No value for")) {
                return "";
            } else {
                throw e;
            }
        }
    }
}
