package com.authandroid_smartcookies.smartcookie.Main.Fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.authandroid_smartcookies.smartcookie.Main.Activities.HomeActivity;
import com.authandroid_smartcookies.smartcookie.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SwitchPreferenceCompat preference = findPreference("coloredTitles");
        if (preference != null) {
            preference.setOnPreferenceChangeListener((preference1, newValue) -> {
                HomeActivity.pref_paintTitles = !HomeActivity.pref_paintTitles;
                return true;
            });
        }
    }
}