package com.authandroid_smartcookies.smartcookie.Main.Fragments;

import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.authandroid_smartcookies.smartcookie.Main.Activities.LauncherActivity;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SwitchPreferenceCompat coloredPreference = findPreference("coloredTitles");
        if (coloredPreference != null) {
            coloredPreference.setOnPreferenceChangeListener((preference1, newValue) -> {
                LauncherActivity.pref_paintTitles = !LauncherActivity.pref_paintTitles;
                return true;
            });
        }

        ListPreference themePrefrence = findPreference("pref_theme");
        if (themePrefrence != null) {
            themePrefrence.setOnPreferenceChangeListener((preference, newValue) -> {
                Utilities.setDeviceThemeMode((String)newValue);
                return true;
            });
        }
    }
}