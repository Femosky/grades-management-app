package com.example.appforapp;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    // Static method to hide the keyboard
    public static void hideKeyboard(View view) {
        // Get the InputMethodManager service
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        // Check if the InputMethodManager is not null
        if (imm != null) {
            // Hide the keyboard
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}