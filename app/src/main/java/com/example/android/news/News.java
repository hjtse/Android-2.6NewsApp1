package com.example.android.news;

import java.util.Date;


public class News {

    // Section of the News
    private String mSectionName;

    // Title of the News
    private String mTitle;

    //Time of Publication
    private Date mWebPublicationDate;

    //URL
    private String mUrl;

    //Author
    private String mAuthor;


    /**
     * Constructs a new {@link News} object.
     *
     * @param sectionName        is the section name
     * @param title              is the article title
     * @param webpublicationdate is the date of publication
     * @param url                is the URL
     * @param author             is the author
     */

    public News(String sectionName, String title, Date webpublicationdate, String url, String author) {
        mSectionName = sectionName;
        mTitle = title;
        mWebPublicationDate = webpublicationdate;
        mUrl = url;
        mAuthor = author;
    }

    /**
     * Get Section Name
     */
    public String getSectionName() {
        return mSectionName;
    }

    /**
     * Get Title of Article
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get date of publication
     */
    public Date getWebPublicationDate() {
        return mWebPublicationDate;
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Get author name
     */
    public String getAuthor() {
        return mAuthor;
    }


}
