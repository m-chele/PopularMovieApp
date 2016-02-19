package com.m_chele.popularmovieapp;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity
    implements Preference.OnPreferenceChangeListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o)
    {
        return false;
    }
}
