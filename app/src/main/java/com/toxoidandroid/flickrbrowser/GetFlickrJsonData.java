package com.toxoidandroid.flickrbrowser;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlickrJsonData extends GetRawData {

    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        mPhotos = new ArrayList<>();
        createAndUpdateUri(searchCriteria, matchAll);
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void execute(){
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "built Uri: " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());
    }

    private boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {

        final String BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAG_PARAM = "tags";
        final String TAG_MODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String CALLBACK_PARAM = "nojsoncallback";

        mDestinationUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(TAG_PARAM, searchCriteria)
                .appendQueryParameter(TAG_MODE_PARAM, matchAll ? "ALL":"ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(CALLBACK_PARAM, "1")
                .build();
        return mDestinationUri != null;
    }

    public void processResult() {
        if (getmDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG, "Error downloading raw file");
        }

        final String FLICKR_TITLE = "title";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_ITEMS = "items";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";

        try {
            JSONObject jsonData = new JSONObject(getmData());
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);
            for (int i = 0; i < itemsArray.length(); i++){
                JSONObject photoObject = itemsArray.getJSONObject(i);
                String title = photoObject.getString(FLICKR_TITLE);
                String author = photoObject.getString(FLICKR_AUTHOR);
                String authorId = photoObject.getString(FLICKR_AUTHOR_ID);
                String tags = photoObject.getString(FLICKR_TAGS);

                JSONObject mediaObject = photoObject.getJSONObject(FLICKR_MEDIA);
                String imgUrl = mediaObject.getString(FLICKR_PHOTO_URL);
                String link = imgUrl.replaceFirst("_m.", "_b.");

                this.mPhotos.add(new Photo(author, title, authorId, link, tags, imgUrl));
            }

            for (Photo singlePhoto: mPhotos){
                Log.v(LOG_TAG, singlePhoto.toString() + "\n");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error parsing json");
        }

    }

    public class DownloadJsonData extends DownloadRawData{
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] par = {mDestinationUri.toString()};
            return super.doInBackground(par);
        }
    }

}
