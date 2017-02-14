package com.task.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.task.R;
import com.task.adapters.TabsAdapter;
import com.task.callbacks.MessageCallback;
import com.task.err.Logger;
import com.task.fragments.ConversationsFragment;
import com.task.models.FavoriteMessageList;
import com.task.models.Message;
import com.task.models.MessageList;
import com.task.network.ApiService;
import com.task.utils.MessageLink;
import com.task.utils.TimeUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.task.activities.HomeActivity.messageList;

public class HomeActivity extends AppCompatActivity implements MessageCallback,
        ConversationsFragment.OnListFragmentInteractionListener
{

    private static ProgressDialog progressDialog;
    private static ApiService.ApiServiceBinder apiServiceBinder;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static MessageList messageList;
    public static FavoriteMessageList favoriteMessageList;
    private TabsAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showAlert(getString(R.string.getting_messages),false);


        // connect to API service
        final Intent apiService = new Intent(this, ApiService.class);
        bindService(apiService, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Logger.add(Logger.DEBUG,"api_service","Connected");
                apiServiceBinder = (ApiService.ApiServiceBinder)iBinder;

                // Fetch all messages
                fetchAllMessages();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Logger.add(Logger.DEBUG,"api_service","Disconnected");
            }
        }, Context.BIND_AUTO_CREATE);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    public void fetchAllMessages() {
        apiServiceBinder.getMessages(HomeActivity.this);
    }


    public synchronized void showAlert(String dialogMessage, boolean allowCancel)
    {
        try{
            if(progressDialog==null)
                progressDialog = new ProgressDialog(this);
            else
                progressDialog.cancel();
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(allowCancel);
            progressDialog.setMessage(dialogMessage);
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleHorizontal);
            progressDialog.show();
        }
        catch (Exception e)
        {
            Logger.add(Logger.WARNING,"show_message",e);
        }

    }
    public void hideAlert()
    {
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    @Override
    public void onMessagesRetreived(MessageList retreivedMessageList) {
        hideAlert();
        messageList = retreivedMessageList;
        favoriteMessageList = new FavoriteMessageList();
        Logger.add(Logger.DEBUG,"get_messages",messageList.toString());
        // @Todo sort list based on time stamp
        // check for consecutive messages by same user within delay of 100 seconds
        try {
            int messageCount = messageList.getCount();
            for (int i = 0; i < messageCount; i++) {
                Message currentMessage = messageList.getMessages().get(i), nextMessage = null, prevMessage = null;
                if(!messageList.messageMap.containsKey(currentMessage.getUsername()))
                    messageList.messageMap.put(currentMessage.getUsername(),currentMessage);
                Date currentMsgTime = TimeUtils.parseDate(currentMessage.getMessageTime());
                if (i + 1 < messageCount) {
                    nextMessage = messageList.getMessages().get(i + 1);
                    if (!nextMessage.getUsername().equals(currentMessage.getUsername()))
                        nextMessage = null;
                    if (nextMessage != null) {
                        Date nextMsgTime = TimeUtils.parseDate(nextMessage.getMessageTime());

                        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(nextMsgTime.getTime() - currentMsgTime.getTime());
                        if (diffInSeconds > 100)
                            nextMessage = null;
                    }

                }
                if (i - 1 >= 0) {
                    prevMessage = messageList.getMessages().get(i - 1);
                    if (!prevMessage.getUsername().equals(currentMessage.getUsername()))
                        prevMessage = null;

                    if(prevMessage!=null)
                    {
                        Date prevMsgTime = TimeUtils.parseDate(prevMessage.getMessageTime());

                        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(currentMsgTime.getTime() - prevMsgTime.getTime());
                        if (diffInSeconds > 100)
                            prevMessage = null;
                    }
                }
                if (prevMessage != null && nextMessage != null)
                    currentMessage.setMessageLinked(MessageLink.INTERMMEDIATE.value());
                else if (prevMessage == null && nextMessage != null)
                    currentMessage.setMessageLinked(MessageLink.FIRST.value());
                else if (prevMessage != null && nextMessage == null)
                    currentMessage.setMessageLinked(MessageLink.LAST.value());
                else
                    currentMessage.setMessageLinked(MessageLink.NONE.value());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        setupTabs();
    }
    @Override
    public void notifyDataChanged()
    {
        tabsAdapter.notifyDataSetChanged();
    }
    private void setupTabs() {
        if(tabLayout.getTabCount()==0)
        {
            tabLayout.addTab(tabLayout.newTab().setText("Conversations"));
            tabLayout.addTab(tabLayout.newTab().setText("Favorites"));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    @Override
    public void onGetMessageFailure(String status)
    {
        hideAlert();
        Logger.add(Logger.SEVERE,"get_messages",status);
    }
}
