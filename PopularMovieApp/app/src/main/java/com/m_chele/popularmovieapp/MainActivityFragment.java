package com.m_chele.popularmovieapp;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivityFragment extends Fragment
{

    private FilmsAdapter filmsAdapter;
    private GridView filmsGridView;

    public MainActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        filmsAdapter = new FilmsAdapter(getActivity());

        filmsGridView = (GridView) rootView.findViewById(R.id.films_gridview);
        filmsGridView.setAdapter(filmsAdapter);

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        AsyncTask<Void, Void, String[]> filmsAsyncTask = new FilmsAsyncTask();
        filmsAsyncTask.execute();
    }


    private class FilmsAsyncTask extends AsyncTask<Void, Void, String[]>
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
                Log.e("MainActivityFragment", "IOException", e);
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
            } catch (JSONException e)
            {
                Log.e("!!!", "Error parsing json", e);
            } finally
            {
                return result;
            }
        }

        @NonNull
        private URL getUrl() throws MalformedURLException
        {
            final String BASE_URI = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY_QUERY_PARAM = "sort_by";
            // TODO: get values from prefs
            final String POPULARITY_DESC_QUERY_VALUE = "popularity.desc";
            final String VOTE_DESC_QUERY_VALUE = getString(R.string.vote_desc_query_value);

            final String API_KEY_QUERY_PARAM = "api_key";

            Uri uri = Uri.parse(BASE_URI).buildUpon()
                    .appendQueryParameter(SORT_BY_QUERY_PARAM, POPULARITY_DESC_QUERY_VALUE)
                    .appendQueryParameter(API_KEY_QUERY_PARAM, getString(R.string.api_key))
                    .build();
            return new URL(uri.toString());
        }


        private String[] getMovieDataFromJson(String jsonStr) throws JSONException
        {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");
            String[] posterImages = new String[resultsArray.length()];
            for (int i = 0; i < resultsArray.length(); i++)
            {
                posterImages[i] = resultsArray.getJSONObject(i).getString("poster_path");
            }
            return posterImages;
        }

        @Override
        protected void onPostExecute(String[] fileImages)
        {
            super.onPostExecute(fileImages);
            filmsAdapter.setData(fileImages);
        }
    }
}
