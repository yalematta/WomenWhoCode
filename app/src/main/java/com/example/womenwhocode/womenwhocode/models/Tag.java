package com.example.womenwhocode.womenwhocode.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by zassmin on 11/9/15.
 */
@ParseClassName("Tag")
public class Tag extends ParseObject {
    public static final String NAME_KEY = "name";

    public void setName(String name) {
        put(NAME_KEY, name);
    }

    public String getName() {
        return get(NAME_KEY).toString();
    }
}
