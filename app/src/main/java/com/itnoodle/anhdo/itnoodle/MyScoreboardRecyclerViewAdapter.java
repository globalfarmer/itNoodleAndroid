package com.itnoodle.anhdo.itnoodle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itnoodle.anhdo.itnoodle.ScoreboardFragment.OnListFragmentInteractionListener;
import com.itnoodle.anhdo.itnoodle.dummy.ScoreboardContent;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.itnoodle.anhdo.itnoodle.dummy.ScoreboardContent.ScoreboardItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyScoreboardRecyclerViewAdapter extends RecyclerView.Adapter<MyScoreboardRecyclerViewAdapter.ViewHolder> {

    private final List<ScoreboardContent.ScoreboardItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyScoreboardRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        mValues = ScoreboardContent.ITEMS;
        ScoreboardContent.loadNextPage(this, context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_scoreboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mCodeView.setText(mValues.get(position).code);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mUploadtime.setText(mValues.get(position).uploadTime);

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
        public final TextView mIdView;
        public final TextView mCodeView;
        public final TextView mNameView;
        public final TextView mUploadtime;
        public ScoreboardContent.ScoreboardItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mCodeView = (TextView) view.findViewById(R.id.code);
            mNameView = (TextView) view.findViewById(R.id.name);
            mUploadtime = (TextView) view.findViewById(R.id.status);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
