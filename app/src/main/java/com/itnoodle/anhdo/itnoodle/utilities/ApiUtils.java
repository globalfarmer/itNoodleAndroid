package com.itnoodle.anhdo.itnoodle.utilities;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * These com.itnoodle.anhdo.itnoodle.utilities will be used to get data from itNoodleService
 */
public class ApiUtils {
    final static String SERVICE_BASE_URL = "http://159.65.138.216";
    final static String STUDENT_BASE = "student";
    final static String ANNOUNCE_BASE = "announce";
    final static String SCOREBOARD_BASE = "scoreboard";
    final static String PARAM_CODE = "code";
    final static String PARAM_TERM = "term";
    final static String PARAM_YEAR = "year";
    final static String PARAM_PAGE = "page";
    final static String PARAM_EDU = "type_edu";

    /**
     * Builds the URL used to query Github.
     *
     * @param stdCode The student code that will be queried for.
     * @param term
     * @param year
     * @return The URL to use to query the weather server.
     */
    public static String getStudentUrl(String stdCode, String term, String year) {
        Uri builtUri = Uri.parse(SERVICE_BASE_URL).buildUpon()
                .appendPath(STUDENT_BASE)
                .appendQueryParameter(PARAM_CODE, stdCode)
                .appendQueryParameter(PARAM_TERM, term)
                .appendQueryParameter(PARAM_YEAR, year)
                .build();

        return builtUri.toString();
    }
    /**
     * Builds the URL used to query Github.
     *
     * @param page The student code that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static String getAnnounceUrl(String page) {
        Uri builtUri = Uri.parse(SERVICE_BASE_URL).buildUpon()
                .appendPath(ANNOUNCE_BASE)
                .appendQueryParameter(PARAM_PAGE, page)
                .build();

        return builtUri.toString();
    }
    /**
     * Builds the URL used to query Github.
     * @param isStar
     * @return The URL to use to query the weather server.
     */
    public static String getAnnounceStarUrl(boolean isStar) {
        String starPath = isStar ? "star" : "unstar";
        Uri builtUri =  Uri.parse(SERVICE_BASE_URL).buildUpon()
                .appendPath(ANNOUNCE_BASE)
                .appendPath("star")
                .build();
        return builtUri.toString();
    }
    /**
     * Builds the URL used to query Github.
     *
     * @param page The student code that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static String getScoreboardUrl(String page, String term, String year) {
        Uri builtUri = Uri.parse(SERVICE_BASE_URL).buildUpon()
                .appendPath(SCOREBOARD_BASE)
                .appendQueryParameter(PARAM_PAGE, page)
                .appendQueryParameter(PARAM_TERM, term)
                .appendQueryParameter(PARAM_YEAR, year)
                .appendQueryParameter(PARAM_EDU, "0")
                .build();

       return builtUri.toString();
    }
    /**
     * Builds the URL used to query Github.
     * @param isStar
     * @return The URL to use to query the weather server.
     */
    public static String getScoreboardStarUrl(boolean isStar) {
        String starPath = isStar ? "star" : "unstar";
        Uri builtUri = Uri.parse(SERVICE_BASE_URL).buildUpon()
                .appendPath(SCOREBOARD_BASE)
                .appendPath(starPath)
                .build();

        return builtUri.toString();
    }
}