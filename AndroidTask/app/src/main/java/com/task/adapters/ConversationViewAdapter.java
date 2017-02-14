package com.task.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.task.R;
import com.task.fragments.ConversationsFragment;
import com.task.models.FavoriteMessageList;
import com.task.models.Message;
import com.task.utils.MessageLink;
import com.task.utils.TimeUtils;

import java.util.Date;

import static com.task.activities.HomeActivity.favoriteMessageList;
import static com.task.activities.HomeActivity.messageList;


public class ConversationViewAdapter extends RecyclerView.Adapter<ConversationViewAdapter.ViewHolder> {


    private final ConversationsFragment.OnListFragmentInteractionListener mListener;

    public ConversationViewAdapter(ConversationsFragment.OnListFragmentInteractionListener listener) {

        mListener = listener;
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

            final RelativeLayout root = (RelativeLayout)holder.itemView.findViewById(R.id.root);
            final Message currentMessage = messageList.getMessages().get(position);
            TextView title = (TextView) holder.itemView.findViewById(R.id.title);
            FrameLayout header = (FrameLayout) holder.itemView.findViewById(R.id.header);
            LinearLayout body = (LinearLayout) holder.itemView.findViewById(R.id.body);
            title.setText(currentMessage.getName());

            final TextView message = (TextView) holder.itemView.findViewById(R.id.contentMsg);

            TextView time = (TextView) holder.itemView.findViewById(R.id.msgTime);
            final ImageView favoriteButton = (ImageView) holder.itemView.findViewById(R.id.favoriteButton);
            final ImageView favoriteButton2 = (ImageView) holder.itemView.findViewById(R.id.favoriteButton2);
            if(favoriteMessageList.isFavorite(currentMessage))
            {
                favoriteButton.setImageDrawable(ContextCompat.getDrawable(root.getContext(),R.mipmap.ic_star_100));
                favoriteButton2.setImageDrawable(ContextCompat.getDrawable(root.getContext(),R.mipmap.ic_star_100));
                favoriteButton.setTag(true);
                favoriteButton2.setTag(true);
            }
            else
            {
                favoriteButton.setImageDrawable(ContextCompat.getDrawable(root.getContext(),R.mipmap.ic_star_0));
                favoriteButton2.setImageDrawable(ContextCompat.getDrawable(root.getContext(),R.mipmap.ic_star_0));
                favoriteButton.setTag(false);
                favoriteButton2.setTag(false);
            }
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isSelected = (Boolean) view.getTag();
                    if(isSelected)
                    {
                        favoriteButton.setTag(false);
                        favoriteMessageList.clearFavorite(currentMessage);
                        favoriteButton.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),R.mipmap.ic_star_0));
                    }
                    else
                    {
                        favoriteButton.setTag(true);
                        favoriteMessageList.setFavorite(currentMessage.getUsername(),currentMessage);
                        favoriteButton.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),R.mipmap.ic_star_100));
                    }
                    mListener.notifyDataChanged();
                }
            });

            // @TODO This can be replaced with single OnClickListener class
            favoriteButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isSelected = (Boolean) view.getTag();
                    if(isSelected)
                    {
                        favoriteButton2.setTag(false);
                        favoriteMessageList.clearFavorite(currentMessage);
                        favoriteButton2.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),R.mipmap.ic_star_0));
                    }
                    else
                    {
                        favoriteButton2.setTag(true);
                        favoriteMessageList.setFavorite(currentMessage.getUsername(),currentMessage);
                        favoriteButton2.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),R.mipmap.ic_star_100));
                    }
                    mListener.notifyDataChanged();
                }
            });
            Date msgTime = TimeUtils.parseDate(currentMessage.getMessageTime());
            String msgBody = currentMessage.getBody();

            try{
                if(currentMessage.getMessageLinked()!=null)
                {
                    MessageLink link = MessageLink.valueOf(currentMessage.getMessageLinked());
                    switch (link)
                    {
                        case FIRST: //
                            header.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.background_rounded_top));
                            break;
                        case INTERMMEDIATE://
                            header.setVisibility(View.GONE);
                            favoriteButton2.setVisibility(View.VISIBLE);
                            root.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.background_rounded));
                            body.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.background_rounded));
                            break;
                        case LAST://
                            header.setVisibility(View.GONE);
                            favoriteButton2.setVisibility(View.VISIBLE);
                            root.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.background_rounded));
                            body.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.background_rounded));
                            break;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            message.setText(msgBody);
            time.setText(TimeUtils.parseTimeString("EEE dd MMM HH:mm a",msgTime));

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
        public Message mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

    }


}
