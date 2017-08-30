package com.example.josepablo.flickrapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jose Pablo on 8/29/2017.
 */

public class GridViewAdapter extends ArrayAdapter<ImageInfo> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<ImageInfo> mGridData = new ArrayList<ImageInfo>();

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<ImageInfo> mGridData) {

        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;

    }

    public void setGridData(ArrayList<ImageInfo> mGridData) {

        this.mGridData = mGridData;
        notifyDataSetChanged();

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        ViewHolder holder;

        if (row == null) {

            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.ivImage);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageInfo item = mGridData.get(position);
        Picasso.with(mContext).load(item.getImgUrl()).into(holder.imageView);
        return row;

    }

    static class ViewHolder {

        ImageView imageView;

    }
}
