package com.example.android.news;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Helper methods related to requesting and receiving news data from Guardian.
 */
public final class QueryUtils {
    private static final String LOG_TAG = NewsActivity.class.getSimpleName();
    static Context mContext;

    public static ArrayList<News> extractNews(String NewsJSon) {
        ArrayList<News> NewsToday = new ArrayList<>();
        if (TextUtils.isEmpty(NewsJSon)) {
            Log.e(mContext.getString(R.string.Query), mContext.getString(R.string.emptyJson));
            return null;
        }
        try {
            JSONObject baseJsonResponse;
            JSONObject NewsResponse;
            JSONArray resultsArray;
            baseJsonResponse = new JSONObject(NewsJSon);
            if (baseJsonResponse.has(mContext.getString(R.string.response))) {
                NewsResponse = baseJsonResponse.getJSONObject(mContext.getResources().getString(R.string.response));

                if (NewsResponse.has(mContext.getString(R.string.results))) {
                    resultsArray = NewsResponse.getJSONArray(mContext.getString(R.string.results));

                    for (int i = 0; i < resultsArray.length(); i++) {
                        String webTitle = "";
                        String sectionName = "";
                        String DateTime = "";
                        String webUrl = "";
                        String thumbnail = "";
                        String contributor = "";
                        JSONObject currentNews = resultsArray.getJSONObject(i);
                        if (currentNews.has(mContext.getString(R.string.webTitle))) {
                            webTitle = currentNews.getString(mContext.getString(R.string.webTitle));
                        }
                        if (currentNews.has(mContext.getString(R.string.sectionName))) {
                            sectionName = currentNews.getString(mContext.getString(R.string.sectionName));
                        }
                        if (currentNews.has(mContext.getString(R.string.webUrl))) {
                            webUrl = currentNews.getString(mContext.getString(R.string.webUrl));
                        }
                        if (currentNews.has(mContext.getString(R.string.webPublicationDate))) {
                            DateTime = currentNews.getString(mContext.getString(R.string.webPublicationDate));
                        }

                        if (currentNews.has(mContext.getString(R.string.tags))) {
                            JSONArray tagsArray = currentNews.getJSONArray(mContext.getString(R.string.tags));
                            if (tagsArray.length() != 0) {
                                JSONObject tags = tagsArray.getJSONObject(0);
                                if (tags.has(mContext.getString(R.string.webTitle))) {
                                    contributor = tags.getString(mContext.getString(R.string.webTitle));
                                }
                            }
                        }
                        News NewNews = new News(webTitle, sectionName, webUrl, DateTime, contributor);
                        NewsToday.add(NewNews);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(mContext.getString(R.string.QueryUtils), mContext.getString(R.string.ProblemWithResults), e);
        }
        return NewsToday;
    }

    public static ArrayList<News> fetchNewsdata(String requestUrl, Context context) {
        mContext = context;
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        ArrayList<News> newsNow = QueryUtils.extractNews(jsonResponse);

        // Return the list of {@link News}s
        return newsNow;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}