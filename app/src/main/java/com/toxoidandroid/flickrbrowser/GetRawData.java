package com.toxoidandroid.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.toxoidandroid.flickrbrowser.DownloadStatus.FAILED_OR_EMPTY;
import static com.toxoidandroid.flickrbrowser.DownloadStatus.IDLE;
import static com.toxoidandroid.flickrbrowser.DownloadStatus.NOT_INITIALISED;
import static com.toxoidandroid.flickrbrowser.DownloadStatus.OK;
import static com.toxoidandroid.flickrbrowser.DownloadStatus.PROCESSING;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK}

public class GetRawData {

    private String LOG_TAG = GetRawData.class.getSimpleName();

    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public GetRawData(String mRawUrl) {
        this.mRawUrl = mRawUrl;
        this.mDownloadStatus = IDLE;
    }

    public void reset(){
        this.mDownloadStatus = IDLE;
        this.mRawUrl = null;
        this.mData = null;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }


    public String getmData() {
        return mData;
    }

    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    public void execute(){
        mDownloadStatus = PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    public class DownloadRawData extends AsyncTask<String, Void, String>{

        @Override
        protected void onPostExecute(String webData) {
            mData = webData;
            if (mData == null){

                if (mRawUrl == null)
                    mDownloadStatus = NOT_INITIALISED;
                else
                    mDownloadStatus = FAILED_OR_EMPTY;
            }else{
//                Log.v(LOG_TAG, "Returned Json: " + mData);
                mDownloadStatus = OK;
            }

        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuffer buffer = new StringBuffer();

            if(params[0] == null){
                mDownloadStatus = NOT_INITIALISED;
                return null;
            }
            try {
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }
                return buffer.toString();

            }catch (IOException e){
                Log.e(LOG_TAG, "Error opening connection", e);
                return null;
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (reader != null){
                    try{
                        reader.close();
                    }catch (IOException e) {
                        Log.e(LOG_TAG, "Error Closing Stream");
                    }
                }
            }
        }
    }

}
