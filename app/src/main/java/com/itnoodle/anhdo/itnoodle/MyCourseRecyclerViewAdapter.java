package com.itnoodle.anhdo.itnoodle;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itnoodle.anhdo.itnoodle.CourseFragment.OnListFragmentInteractionListener;
import com.itnoodle.anhdo.itnoodle.dummy.CourseContent;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.itnoodle.anhdo.itnoodle.dummy.CourseContent.CourseItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCourseRecyclerViewAdapter extends RecyclerView.Adapter<MyCourseRecyclerViewAdapter.ViewHolder> {
    private static final String LOG_TAG = "MY_COURSE_RECYCLE_VIEW_ADAPTER";

    private final List<CourseContent.CourseItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyCourseRecyclerViewAdapter(List<CourseContent.CourseItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mCode.setText(mValues.get(position).code);
        holder.mName.setText(mValues.get(position).name);
        Log.i(LOG_TAG, mValues.get(position).toString());
        if(!TextUtils.isEmpty(mValues.get(position).day) && mValues.get(position).day != null) {
            Log.i(LOG_TAG, "TIME IS NOT NULL");
            holder.mCourseNoFinaltest.setVisibility(View.GONE);
            holder.mCourseHaveFinaltest.setVisibility(LinearLayout.VISIBLE);
            holder.mFinaltestTime.setText(mValues.get(position).day);
            holder.mFinaltestRoom.setText(mValues.get(position).room);
            holder.mFinaltestType.setText(mValues.get(position).type);
        }
        else {
            Log.i(LOG_TAG, "TIME IS NULL");
        }
        if(mValues.get(position).url != null) {
            Log.i(LOG_TAG, "URL is NOT NULL");
            holder.mCourseNoScoreboard.setVisibility(View.GONE);
            holder.mCourseHaveScoreboard.setVisibility(View.VISIBLE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCode;
        public final TextView mName;
        public final TextView mCourseNoFinaltest;
        public final LinearLayout mCourseHaveFinaltest;
        public final TextView mCourseNoScoreboard;
        public final TextView mCourseHaveScoreboard;
        public final TextView mFinaltestTime;
        public final TextView mFinaltestRoom;
        public final TextView mFinaltestType;
        public CourseContent.CourseItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCode = (TextView) view.findViewById(R.id.course_code);
            mName = (TextView) view.findViewById(R.id.course_name);
            mCourseNoFinaltest = (TextView) view.findViewById(R.id.course_no_finaltest_label);
            mCourseHaveFinaltest = (LinearLayout) view.findViewById(R.id.course_have_finaltest);
            mCourseNoScoreboard = (TextView) view.findViewById(R.id.course_no_scoreboard_label);
            mCourseHaveScoreboard = (TextView) view.findViewById(R.id.course_have_scoreboard);
            mFinaltestTime = (TextView) view.findViewById(R.id.course_finaltest_time);
            mFinaltestRoom = (TextView) view.findViewById(R.id.course_finaltest_room);
            mFinaltestType = (TextView) view.findViewById(R.id.course_finaltest_type);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCode.getText() + "' " + mName.getText();
        }
    }
}
