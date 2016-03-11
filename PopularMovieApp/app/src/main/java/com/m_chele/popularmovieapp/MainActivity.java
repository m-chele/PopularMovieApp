package com.m_chele.popularmovieapp;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
{

    private MainActivityFragment mainFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFragment = new MainActivityFragment();
        settingsFragment = new SettingsFragment();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, mainFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, settingsFragment)
                    .addToBackStack(null) // != null should be used for fragments without UI
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
        } else
        {
            super.onBackPressed();
        }
    }
}
