package com.itnoodle.anhdo.itnoodle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itnoodle.anhdo.itnoodle.AnnounceFragment.OnListFragmentInteractionListener;
import com.itnoodle.anhdo.itnoodle.dummy.AnnounceContent;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.itnoodle.anhdo.itnoodle.dummy.AnnounceContent.AnnounceItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAnnounceRecyclerViewAdapter extends RecyclerView.Adapter<MyAnnounceRecyclerViewAdapter.ViewHolder> {

    private final List<AnnounceContent.AnnounceItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAnnounceRecyclerViewAdapter(Context context, OnListFragmentInteractionListener listener) {
        mValues = AnnounceContent.ITEMS;
        AnnounceContent.loadNextPage(this, context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_announce, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).title);
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
        public final TextView mContentView;
        public final TextView mUploadtime;
        public AnnounceContent.AnnounceItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mUploadtime = (TextView) view.findViewById(R.id.uploadtime);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
