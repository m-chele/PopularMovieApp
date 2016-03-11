package com.m_chele.popularmovieapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class SettingsFragment extends PreferenceFragment
{
    private SharedPreferences sharedPreferences;

    private final SharedPreferences.OnSharedPreferenceChangeListener sharedPrefsChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener()
            {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
                {
                    if (getString(R.string.pref_sort_order_key).equals(key))
                    {
                        Log.d("!!!", "key is pref_sort_order ");
                        Preference preference = findPreference(key);
                        preference.setSummary(sharedPreferences.getString(key, ""));
                    }
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPrefsChangeListener);
    }

    @Override
    public void onPause()
    {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPrefsChangeListener);
        super.onPause();
    }
}
