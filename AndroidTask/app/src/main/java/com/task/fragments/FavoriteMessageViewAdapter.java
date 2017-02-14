package com.task.fragments;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.task.R;
import com.task.models.Message;
import com.task.utils.ImageLoader;

import java.util.ArrayList;

import static com.task.activities.HomeActivity.favoriteMessageList;
import static com.task.activities.HomeActivity.messageList;

public class FavoriteMessageViewAdapter extends RecyclerView.Adapter<FavoriteMessageViewAdapter.ViewHolder> {

    private final FavoritesFragment.OnListFragmentInteractionListener mListener;
    ArrayList<String> userNames = new ArrayList<>();

    public FavoriteMessageViewAdapter(FavoritesFragment.OnListFragmentInteractionListener listener) {
        mListener = listener;
        userNames = new ArrayList<>();
        userNames.addAll(messageList.messageMap.keySet());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_message_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Message message = holder.mItem = messageList.messageMap.get(userNames.get(position));
        try{
            ImageLoader.loadImage(holder.profileImage,message.getImageUrl(),R.drawable.profile,holder.itemView.getContext(),holder.imageProgress);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        holder.userName.setText(message.getUsername());
        holder.name.setText(message.getName());
        int totalCount = favoriteMessageList.totalMessageCount.containsKey(message.getUsername())?favoriteMessageList.totalMessageCount.get(message.getUsername()):0;
        holder.messageCount.setText(String.valueOf(totalCount));
        int favoriteCount = favoriteMessageList.favoriteMessageMap.containsKey(message.getUsername())?favoriteMessageList.favoriteMessageMap.get(message.getUsername()).size():0;
        holder.favoriteCount.setText(String.valueOf(favoriteCount));
        if(holder.favoriteCount.getText().toString().equals("0"))
        {
            holder.favoriteCountImage.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),R.mipmap.ic_star_0));
        }
        else
            holder.favoriteCountImage.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),R.mipmap.ic_star_100));


    }

    @Override
    public int getItemCount() {
        return favoriteMessageList.totalMessageCount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView userName, name, messageCount,favoriteCount;
        private final ProgressBar imageProgress;
        public Message mItem;
        public ImageView profileImage;
        public ImageView favoriteCountImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            userName = (TextView) view.findViewById(R.id.userName);
            name = (TextView) view.findViewById(R.id.name);
            messageCount = (TextView) view.findViewById(R.id.messageCount);
            favoriteCount = (TextView) view.findViewById(R.id.favoriteCount);
            profileImage = (ImageView) view.findViewById(R.id.profileImage);
            imageProgress = (ProgressBar) view.findViewById(R.id.imageProgress);
            favoriteCountImage = (ImageView) view.findViewById(R.id.favoriteCountImage);
        }

    }
}
