package com.itnoodle.anhdo.itnoodle.dummy;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itnoodle.anhdo.itnoodle.MyScoreboardRecyclerViewAdapter;
import com.itnoodle.anhdo.itnoodle.utilities.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardContent {
    private static final String LOG_TAG = "SCOREBOARD_CONTENT";
    /**
     * An array of sample (dummy) items.
     */
    public static final List<ScoreboardItem> ITEMS = new ArrayList<ScoreboardItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ScoreboardItem> ITEM_MAP = new HashMap<String, ScoreboardItem>();
    static private int lastPage = 0;
    static private RequestQueue queue;

    private static void addItem(ScoreboardItem item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    static private String nextPage() {
        lastPage++;
        return Integer.toString(lastPage);
    }

    static public void loadNextPage(final MyScoreboardRecyclerViewAdapter mAdapter, Context context) {
        queue = Volley.newRequestQueue(context);
        queue.add(new StringRequest(Request.Method.GET, ApiUtils.getScoreboardUrl(nextPage(), "1", "2017"),
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.i(LOG_TAG, response);
                        try {
                            JSONObject res = new JSONObject(response);
                            int page = Integer.parseInt(res.getString("page"));
                            JSONArray scoreboards = res.getJSONArray("content");
                            JSONObject scoreboard;
                            for(int i = 0; i < scoreboards.length(); i++) {
                                scoreboard = scoreboards.getJSONObject(i);
                                Log.i(LOG_TAG, scoreboard.getString(ScoreboardItem.URL));
                                addItem(new ScoreboardItem(
                                        Integer.toString(i+1),
                                        scoreboard.optString(ScoreboardItem.COURSE_CODE),
                                        scoreboard.optString(ScoreboardItem.COURSE_NAME),
                                        scoreboard.optString(ScoreboardItem.URL),
                                        scoreboard.optString(ScoreboardItem.UPLOAD_TIME),
                                        false
                                ));
                            }
                            mAdapter.notifyDataSetChanged();
                            Log.i(LOG_TAG, "ITEM_LENGTH");
                            Log.i(LOG_TAG, Integer.toString(ITEMS.size()));
                        } catch(final JSONException e) {
                            Log.e(LOG_TAG, "Parse JSON failed");
                            Log.e(LOG_TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "request get scoreboards failed");
            }
        }
        ));
    }
    public static class ScoreboardItem {
        public static final String ID = "id";
        public static final String COURSE_NAME = "name";
        public static final String COURSE_CODE = "code";
        public static final String URL = "public_src";
        public static final String UPLOAD_TIME = "uploadtime";
        public static final String SCOREBOARD_NOT_UPLOADED = "Chưa có kết quả";
        public String id;
        public String name;
        public String code;
        public String url;
        public String uploadTime;
        public boolean isStar;
        public ScoreboardItem() {}
        public ScoreboardItem(String id, String courseCode, String courseName, String url, String uploadTime, boolean isStar) {
            Log.i(LOG_TAG, id);
            Log.i(LOG_TAG, courseCode);
            Log.i(LOG_TAG, courseName);
            Log.i(LOG_TAG, url);
            this.id = id;
            this.code= courseCode;
            this.name = courseName;
            this.url = url;

            if(!TextUtils.isEmpty(uploadTime.trim()))
                this.uploadTime = uploadTime;
            else
                this.uploadTime = SCOREBOARD_NOT_UPLOADED;
            this.isStar = isStar;
        }
    };
}
