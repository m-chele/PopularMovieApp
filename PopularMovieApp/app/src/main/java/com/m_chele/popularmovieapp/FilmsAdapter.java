package com.m_chele.popularmovieapp;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

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
        ImageView imageView;
        if (view == null)
        {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else
        {
            imageView = (ImageView) view;
        }

        imageView.setImageResource(R.drawable.file_img_1);
        return imageView;
    }
}