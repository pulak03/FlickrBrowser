package com.toxoidandroid.flickrbrowser;


import java.io.Serializable;

public class Photo implements Serializable {

    public static final long serialVersionID = 1L;
    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mLink;
    private String mTags;
    private String mImage;

    public Photo(String mAuthor, String mTitle, String mAuthorId, String mLink, String mTags, String mImage) {
        this.mAuthor = mAuthor;
        this.mTitle = mTitle;
        this.mAuthorId = mAuthorId;
        this.mLink = mLink;
        this.mTags = mTags;
        this.mImage = mImage;
    }

    public static long getSerialVersionID() {
        return serialVersionID;
    }

    public String getTags() {
        return mTags;
    }

    public String getAuthorId() {
        return mAuthorId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getLink() {
        return mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
