package com.toxoidandroid.flickrbrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class FlickrImageViewHolder extends RecyclerView.ViewHolder {

    protected ImageView thumbnail;
    protected TextView title;

    public FlickrImageViewHolder(View itemView) {
        super(itemView);
        this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        this.title = (TextView) itemView.findViewById(R.id.title);
    }
}
