package com.authandroid_smartcookies.smartcookie.Util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Documentation
 */
public class GenericTextWatcher implements TextWatcher {
    private final Runnable method;
    public GenericTextWatcher(Runnable method) {
        this.method = method;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        method.run();
    }
}
