package com.m_chele.popularmovieapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
{

    public MainActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView filmsGridView = (GridView) rootView.findViewById(R.id.films_gridview);
        BaseAdapter filmsAdapter = new BaseAdapter()
        {
            @Override
            public int getCount()
            {
                return 4;
            }

            @Override
            public Object getItem(int i)
            {
                return "Pippo " + i;
            }

            @Override
            public long getItemId(int i)
            {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup)
            {
                TextView textView = new TextView(getContext());
                textView.setText(getItem(i) + "");
                return textView;
            }
        };
        filmsGridView.setAdapter(filmsAdapter);


        return rootView;
    }
}
