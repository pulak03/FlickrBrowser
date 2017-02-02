package com.toxoidandroid.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailPhotoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        activateToolbarWithHomeEnabled();

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);

        TextView titleText = (TextView) findViewById(R.id.photo_title);
        titleText.setText("Title: " + photo.getTitle());

        TextView authorText = (TextView) findViewById(R.id.photo_author);
        authorText.setText("Author: " + photo.getAuthor());

        TextView tagsText = (TextView) findViewById(R.id.photo_tags);
        tagsText.setText("Tags: " + photo.getTags());

        ImageView image = (ImageView) findViewById(R.id.photo_image);
        Picasso.with(this).load(photo.getLink()).
                placeholder(R.drawable.placeholder).
                error(R.drawable.placeholder).into(image);

    }

}
