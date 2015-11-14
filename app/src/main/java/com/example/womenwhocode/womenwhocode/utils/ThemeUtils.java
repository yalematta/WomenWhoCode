package com.example.womenwhocode.womenwhocode.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.womenwhocode.womenwhocode.R;

/**
 * Created by zassmin on 11/13/15.
 */
public class ThemeUtils {
    private static int sTheme = 0;

    public final static int THEME_WWCODE_LIGHT = 0;
    public final static int THEME_WWCODE_DARK = 1;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_WWCODE_LIGHT:
                activity.setTheme(R.style.Theme_WWCodeLight);
                break;
            case THEME_WWCODE_DARK:
                activity.setTheme(R.style.Theme_WWCodeDark);
                break;
        }
    }

}
