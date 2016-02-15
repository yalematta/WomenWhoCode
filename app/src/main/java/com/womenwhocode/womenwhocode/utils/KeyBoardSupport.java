package com.womenwhocode.womenwhocode.utils;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by opticod(Anupam Das) on 15/2/16.
 */
public class KeyBoardSupport {
    public static void helperHideKeyboard(View view, final Activity activity) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    View view = activity.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return false;
                }
            });
        }
    }
}