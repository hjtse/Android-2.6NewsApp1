package com.example.android.news;

import java.util.Date;


public class News {
    private String mSectionName;
    private String mTitle;
    private String mUrl;
    private String mAuthor;
    private String mDateTime;

    /**
     * Constructs a new {@link News} object.
     *
     * @param sectionName is the section name
     * @param title       is the article title
     * @param url         is the URL
     * @param author      is the author
     * @param DateTime    is the date and time
     */

    public News(String sectionName, String title, String url, String author, String DateTime) {
        mSectionName = sectionName;
        mTitle = title;
        mUrl = url;
        mAuthor = author;
        mDateTime = DateTime;
    }


    public String getSectionName() {
        return mSectionName;
    }

    public String getTitle() {
        return mTitle;
    }


    public String getUrl() {
        return mUrl;
    }


    public String getAuthor() {
        return mAuthor;
    }

    public String getDateTime() {
        return mDateTime;
    }


}
