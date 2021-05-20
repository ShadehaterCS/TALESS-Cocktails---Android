package com.authandroid_smartcookies.smartcookie.Main.Fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.authandroid_smartcookies.smartcookie.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}