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

/**
 * A placeholder fragment containing a simple view.
 */
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

        filmsAdapter = new FilmsAdapter(getContext());
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

    private String[] getMovieDataFromJson(String jsonStr)
    {
        String[] posterImages = new String[0];

        try
        {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");
            posterImages = new String[resultsArray.length()];
            for (int i = 0; i < resultsArray.length(); i++)
            {
                posterImages[i] = resultsArray.getJSONObject(i).getString("poster_path");
            }
        } catch (JSONException e)
        {
            Log.e("!!!", e.getMessage());
        } finally
        {
            return posterImages;
        }
    }

    @NonNull
    private URL getUrl() throws MalformedURLException
    {
        // http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=3aaf4cc23bea928c2feb87b85b9f8838/
        Uri uri = Uri.parse("http://api.themoviedb.org/3/discover/movie?").buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", getString(R.string.api_key))
                .build();
        return new URL(uri.toString());
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

                // TODO: if BuildConf == DEBUG ?
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
                Log.d("!!!", "background result: " + result.length);

                return result;
            }
        }


        @Override
        protected void onPostExecute(String[] fileImages)
        {
            super.onPostExecute(fileImages);
            filmsAdapter.setData(fileImages);
        }
    }
}
