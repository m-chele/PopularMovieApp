package com.m_chele.popularmovieapp;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FilmsAdapter extends BaseAdapter
{
    private String[] fileImages;
    private Context mContext;

    public FilmsAdapter(Context mContext)
    {
        this.mContext = mContext;
        this.fileImages = new String[0];
    }

    public void setData(String[] fileImages)
    {
        this.fileImages = fileImages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return fileImages.length;
    }

    @Override
    public Object getItem(int i)
    {
        return fileImages[i];
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }


//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup)
//    {
//        if (null == view)
//        {
//            view = new ImageView(mContext);
//        }
//
//        if (fileImages.length > 0)
//        {
//            Picasso.with(mContext)
//                    .load("http://image.tmdb.org/t/p/w185" + fileImages[i])
//                    .placeholder(R.drawable.film_placeholder) // TODO: use right dimension!
//                    .error(R.drawable.image_unavailable) // TODO: use right dimension!
//                    .fit()
//                    .tag(mContext)
//                    .into((ImageView) view);
//        }
//        return view;
//    }


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

        if (fileImages.length > 0)
        {
            Picasso.with(mContext)
                    .load("http://image.tmdb.org/t/p/w185" + fileImages[i])
                    .placeholder(R.drawable.film_placeholder) // TODO: use right dimension!
                    .error(R.drawable.image_unavailable) // TODO: use right dimension!
                    .fit()
                    .tag(mContext)
                    .into(holder.image);
        }
        return view;
    }

    static class ViewHolder
    {
        ImageView image;
    }
}