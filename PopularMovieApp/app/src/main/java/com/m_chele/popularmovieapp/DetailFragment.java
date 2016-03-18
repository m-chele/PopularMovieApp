package com.m_chele.popularmovieapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        getActivity().setTitle(filmModel.getString(getString(R.string.key_original_title)));

        ImageView imageView = ((ImageView) rootView.findViewById(R.id.film_poster_textview));

        Picasso.with(getActivity())
                .load(getImageUrlForFilm())
                .placeholder(R.drawable.film_placeholder)
                .error(R.drawable.image_unavailable)
                .fit()
                .into(imageView);


        ((TextView) rootView.findViewById(R.id.film_overview_textview))
                .setText(filmModel.getString(getString(R.string.key_overview)));

        ((TextView) rootView.findViewById(R.id.film_release_date_textview))
                .setText("release date: " + filmModel.getString(getString(R.string.key_release_date)));

        return rootView;
    }


    // TODO: duplicated with FilmAdapter
    private String getImageUrlForFilm()
    {
        final String IMAGE_BASE_URI = "http://image.tmdb.org/t/p";
        final String IMAGE_SIZE = "/w185";

        return IMAGE_BASE_URI + IMAGE_SIZE +
                filmModel.getString(getActivity().getString(R.string.key_poster_path));
    }
}
