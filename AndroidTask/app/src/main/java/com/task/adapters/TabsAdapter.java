package com.task.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.task.fragments.ConversationsFragment;
import com.task.fragments.FavoritesFragment;
import com.task.models.FavoriteMessageList;
import com.task.models.MessageList;


public class TabsAdapter extends FragmentPagerAdapter
{

    private MessageList messageList;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setMessageList(MessageList messageList)
    {
        this.messageList = messageList;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position)
        {
            case 0 :
                ConversationsFragment conversationsFragment = new ConversationsFragment();
                conversationsFragment.setMessageList(messageList);
                fragment = conversationsFragment;
                break;
            case 1 :
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                FavoriteMessageList favoriteMessageList = new FavoriteMessageList();
                favoriteMessageList.setMessages(messageList.getMessages());
                favoriteMessageList.setCount(messageList.getCount());
                favoritesFragment.setMessageList(favoriteMessageList);
                fragment = favoritesFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
