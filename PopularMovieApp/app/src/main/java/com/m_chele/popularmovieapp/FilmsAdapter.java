package com.m_chele.popularmovieapp;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmsAdapter extends BaseAdapter
{
    private ArrayList<Bundle> filmsList;
    private Context mContext;

    public FilmsAdapter(Context mContext)
    {
        this.mContext = mContext;
        this.filmsList = new ArrayList();
    }

    public void setData(ArrayList fileImages)
    {
        this.filmsList = fileImages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return filmsList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return filmsList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder holder;
        if (null == view)
        {
            holder = new ViewHolder();
            view = new ImageView(mContext);
            holder.image = (ImageView) view;
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }

        if (filmsList.size() > 0)
        {

            Picasso.with(mContext)
                    .load(getImageUrlForFilm(i))
                    .placeholder(R.drawable.film_placeholder)
                    .error(R.drawable.image_unavailable)
                    .fit()
                    .tag(mContext)
                    .into(holder.image);
        }
        return view;
    }

    private String getImageUrlForFilm(int position)
    {
        final String IMAGE_BASE_URI = "http://image.tmdb.org/t/p";
        final String IMAGE_SIZE = "/w185";

        return IMAGE_BASE_URI + IMAGE_SIZE +
                filmsList.get(position).get(mContext.getString(R.string.key_poster_path));
    }

    static class ViewHolder
    {
        ImageView image;
    }
}