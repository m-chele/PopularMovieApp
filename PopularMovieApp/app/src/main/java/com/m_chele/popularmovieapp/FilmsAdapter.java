package com.m_chele.popularmovieapp;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FilmsAdapter extends BaseAdapter
{
    private Context mContext;

    public FilmsAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

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

//        Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        ImageView imageView = new ImageView(mContext);
        Picasso.with(mContext).load(R.drawable.file_img_1).into(imageView);
        return imageView;
    }
}