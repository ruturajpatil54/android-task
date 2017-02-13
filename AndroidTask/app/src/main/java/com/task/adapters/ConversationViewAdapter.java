package com.task.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.R;
import com.task.fragments.ConversationsFragment;
import com.task.models.Message;
import com.task.models.MessageList;


public class ConversationViewAdapter extends RecyclerView.Adapter<ConversationViewAdapter.ViewHolder> {

    MessageList messageList;
    private final ConversationsFragment.OnListFragmentInteractionListener mListener;

    public ConversationViewAdapter(MessageList messageList, ConversationsFragment.OnListFragmentInteractionListener listener) {
        this.messageList = messageList;
        mListener = listener;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try{
            TextView title = (TextView) holder.itemView.findViewById(R.id.title);
            title.setText(messageList.getMessages().get(position).getName());
            TextView message = (TextView) holder.itemView.findViewById(R.id.contentMsg);
            message.setText(messageList.getMessages().get(position).getBody());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
