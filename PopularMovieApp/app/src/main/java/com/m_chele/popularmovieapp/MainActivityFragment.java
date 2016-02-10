package com.m_chele.popularmovieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        FilmsAdapter filmsAdapter = new FilmsAdapter(getContext());

        filmsGridView.setAdapter(filmsAdapter);

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        new AsyncTask<Void, Void, String[]>()
        {
            @Override
            protected String[] doInBackground(Void... params)
            {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                String responseJsonStr = null;
                String[] result = null;
                try
                {
                    URL url = getUrl();

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null)
                    {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        Log.d("!!!", line);
                        // add \n for debug purpose
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0)
                    {
                        // Stream was empty. No point in parsing.
                        return null;
                    }
                    responseJsonStr = buffer.toString();
                } catch (IOException e)
                {
                    // error
                    return null;
                } finally
                {
                    if (urlConnection != null)
                    {
                        urlConnection.disconnect();
                    }
                    if (reader != null)
                    {
                        try
                        {
                            reader.close();
                        } catch (final IOException e)
                        {
                            Log.e("PlaceholderFragment", "Error closing stream", e);
                        }
                    }
                }


                try
                {
                    result = getMovieDataFromJson(responseJsonStr);
//                } catch (JSONException e)
//                {
//                    Log.e("!!!", "Error parsing json", e);
                } finally
                {
                    Log.d("!!!", "background result: " + result);

                    return result;
                }

            }
        }.execute();
    }

    private String[] getMovieDataFromJson(String jsonStr)
    {
        Log.d("!!!", jsonStr);
        return new String[]{"pippo", "pluto", "paperino", "pollo"};
    }

    @NonNull
    private URL getUrl() throws MalformedURLException
    {
        // http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=3aaf4cc23bea928c2feb87b85b9f8838/
        Uri uri = Uri.parse("http://api.themoviedb.org/3/discover/movie?").buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", "3aaf4cc23bea928c2feb87b85b9f8838")
                .build();
        return new URL(uri.toString());
    }
}
