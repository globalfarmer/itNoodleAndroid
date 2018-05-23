package com.itnoodle.anhdo.itnoodle.dummy;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itnoodle.anhdo.itnoodle.MyAnnounceRecyclerViewAdapter;
import com.itnoodle.anhdo.itnoodle.utilities.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnounceContent {
    public final static String LOG_TAG = "ANNOUNCE_CONTENT";
    /**
     * An array of sample (dummy) items.
     */
    public static final List<AnnounceContent.AnnounceItem> ITEMS = new ArrayList<AnnounceContent.AnnounceItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, AnnounceContent.AnnounceItem> ITEM_MAP = new HashMap<String, AnnounceContent.AnnounceItem>();

    static private int lastPage = 0;
    static private RequestQueue queue;

    private static void addItem(AnnounceItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    static private String nextPage() {
        lastPage++;
        return Integer.toString(lastPage);
    }

    static public void loadNextPage(final MyAnnounceRecyclerViewAdapter mAdapter, Context context) {
        queue = Volley.newRequestQueue(context);
        queue.add(new StringRequest(Request.Method.GET, ApiUtils.getAnnounceUrl(nextPage()),
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.i(LOG_TAG, response);
                        try {
                            JSONObject res = new JSONObject(response);
                            int itemSize = ITEMS.size();
                            JSONArray announces = res.getJSONArray("content");
                            JSONObject announce;
                            for(int i = 0; i < announces.length(); i++) {
                                announce = announces.getJSONObject(i);
                                Log.i(LOG_TAG, announce.getString(AnnounceItem.URL));
                                addItem(new AnnounceItem(
                                    Integer.toString(itemSize+i+1),
                                    announce.getString(AnnounceItem.THUMBNAIL_IMG),
                                    announce.getString(AnnounceItem.TITLE),
                                    announce.getString(AnnounceItem.URL),
                                    announce.getString(AnnounceItem.UPLOAD_TIME),
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
                        Log.e(LOG_TAG, "request get announces failed");
                    }
                }
        ));
    }

    public static class AnnounceItem {
        public static final String ID = "id";
        public static final String THUMBNAIL_IMG = "thumbail_img";
        public static final String TITLE = "title";
        public static final String URL = "url";
        public static final String UPLOAD_TIME = "uploadtime";
        public String id;
        public String thumbnailImg;
        public String title;
        public String url;
        public String uploadTime;
        public boolean isStar;
        public AnnounceItem() {}
        public AnnounceItem(String id, String thumbnailImg, String title, String url, String uploadTime, boolean isStar) {
            Log.i(LOG_TAG, id);
            Log.i(LOG_TAG, thumbnailImg);
            Log.i(LOG_TAG, title);
            Log.i(LOG_TAG, url);
            this.id = id;
            this.thumbnailImg = thumbnailImg;
            this.title = title;
            this.url = url;
            this.uploadTime = uploadTime;
            this.isStar = isStar;
        }
    }
}
