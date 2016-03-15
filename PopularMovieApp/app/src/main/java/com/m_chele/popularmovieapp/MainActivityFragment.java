package com.m_chele.popularmovieapp;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.HashMap;

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
        filmsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Log.d("!!!", "item clicked" + filmsAdapter.getItem(position));

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, new DetailFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        AsyncTask<Void, Void, ArrayList> filmsAsyncTask = new FilmsAsyncTask();
        filmsAsyncTask.execute();
    }


    private class FilmsAsyncTask extends AsyncTask<Void, Void, ArrayList>
    {
        @Override
        protected ArrayList doInBackground(Void... params)
        {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String responseJsonStr = null;
            ArrayList result = null;
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
            final String SORT_BY_QUERY_VALUE = getPreferredSortOrderValue();
            final String API_KEY_QUERY_PARAM = "api_key";

            Uri uri = Uri.parse(BASE_URI).buildUpon()
                    .appendQueryParameter(SORT_BY_QUERY_PARAM, SORT_BY_QUERY_VALUE)
                    .appendQueryParameter(API_KEY_QUERY_PARAM, getString(R.string.api_key))
                    .build();
            return new URL(uri.toString());
        }

        private String getPreferredSortOrderValue()
        {
            return PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getString(getString(R.string.pref_sort_order_key), getString(R.string.vote_desc_query_value));
        }


        private ArrayList getMovieDataFromJson(String jsonStr) throws JSONException
        {
            //TODO: creare oggetto model per film
//            original title:
            //            original_title: "Deadpool",
//            movie poster image thumbnail
            //            poster_path: "/inVq3FRqcYIRl2la8iZikYYxFNR.jpg",

//            A plot synopsis (called overview in the api)
            //                overview: "Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.",
//            user rating (called vote_average in the api)
            //                vote_average: 7.22

//            release date
            //                release_date: "2016-02-09",


//                         "/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg"
//            poster_path: "/inVq3FRqcYIRl2la8iZikYYxFNR.jpg",
//                    adult: false,
//                overview: "Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.",
//                release_date: "2016-02-09",
//                genre_ids: [
//            28,
//                    12,
//                    35
//            ],
//            id: 293660,
//                    original_title: "Deadpool",
//                original_language: "en",
//                title: "Deadpool",
//                backdrop_path: "/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg",
//                popularity: 50.808823,
//                vote_count: 1991,
//                video: false,
//                vote_average: 7.22


            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray resultsArray = jsonObject.getJSONArray("results");
            ArrayList filmsList = new ArrayList(resultsArray.length());
            for (int index = 0; index < resultsArray.length(); index++)
            {
                HashMap<String, String> film = new HashMap<>(3);
                film.put("poster_path", resultsArray.getJSONObject(index).getString(getString(R.string.key_poster_path)));
                filmsList.add(index, film);
            }
            return filmsList;
        }

        @Override
        protected void onPostExecute(ArrayList fileImages)
        {
            super.onPostExecute(fileImages);
            filmsAdapter.setData(fileImages);
        }
    }
}
