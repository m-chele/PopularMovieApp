package com.m_chele.popularmovieapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment
{

    private Bundle filmModel;

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
        filmModel = args;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ((TextView) rootView.findViewById(R.id.film_title_textview))
                .setText(filmModel.getString(getString(R.string.key_poster_path)));

        return rootView;
    }
}
