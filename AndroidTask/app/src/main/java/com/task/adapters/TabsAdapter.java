package com.task.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.task.fragments.ConversationsFragment;
import com.task.fragments.FavoritesFragment;


public class TabsAdapter extends FragmentPagerAdapter
{


    private ConversationsFragment conversationsFragment;
    private FavoritesFragment favoritesFragment;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position)
        {
            case 0 :
                conversationsFragment = new ConversationsFragment();
                fragment = conversationsFragment;
                break;
            case 1 :
                favoritesFragment = new FavoritesFragment();
                fragment = favoritesFragment;
                break;
        }
        return fragment;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        favoritesFragment.notifyDataChanged();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
