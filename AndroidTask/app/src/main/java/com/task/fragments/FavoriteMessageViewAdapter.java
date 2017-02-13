package com.task.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.R;
import com.task.models.FavoriteMessageList;
import com.task.models.Message;

public class FavoriteMessageViewAdapter extends RecyclerView.Adapter<FavoriteMessageViewAdapter.ViewHolder> {

    private final FavoriteMessageList messageList;
    private final FavoritesFragment.OnListFragmentInteractionListener mListener;

    public FavoriteMessageViewAdapter(FavoriteMessageList messageList, FavoritesFragment.OnListFragmentInteractionListener listener) {
        this.messageList = messageList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favoritemessage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = messageList.getMessages().get(position);
        holder.mIdView.setText(messageList.getMessages().get(position).getName());

    }

    @Override
    public int getItemCount() {
        return messageList.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Message mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
