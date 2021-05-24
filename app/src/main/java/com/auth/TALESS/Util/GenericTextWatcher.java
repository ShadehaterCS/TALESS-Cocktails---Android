package com.auth.TALESS.Util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Implements the TextWatcher Interface
 * Main use is to be unobtrusive inside the other classes that need to use it
 * Constructor takes a Runnable (use case: a method) that runs every time the text has been
 * changed inside a TextView that can take a TextWatcher.
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
