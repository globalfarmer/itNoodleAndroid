package com.itnoodle.anhdo.itnoodle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//        holder.mCredit.setText(mValues.get(position).credit);
//        holder.mGroup.setText(mValues.get(position).group);
//        holder.mNote.setText(mValues.get(position).note);

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
//        public final TextView mCredit;
//        public final TextView mGroup;
//        public final TextView mNote;
        public CourseContent.CourseItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCode = (TextView) view.findViewById(R.id.course_code);
            mName = (TextView) view.findViewById(R.id.course_name);
//            mCredit = (TextView) view.findViewById(R.id.course_credit);
//            mGroup = (TextView) view.findViewById(R.id.course_group);
//            mNote = (TextView) view.findViewById(R.id.course_note);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCode.getText() + "' " + mName.getText();
        }
    }
}
