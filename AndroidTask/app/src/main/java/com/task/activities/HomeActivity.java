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
import com.task.models.MessageList;
import com.task.network.ApiService;

public class HomeActivity extends AppCompatActivity implements MessageCallback {

    private static ProgressDialog progressDialog;
    private static ApiService.ApiServiceBinder apiServiceBinder;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MessageList messageList;

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
                apiServiceBinder.getMessages(HomeActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Logger.add(Logger.DEBUG,"api_service","Disconnected");
            }
        }, Context.BIND_AUTO_CREATE);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

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
    public void onMessagesRetreived(MessageList messageList) {
        hideAlert();
        this.messageList = messageList;
        Logger.add(Logger.DEBUG,"get_messages",messageList.toString());
        setupTabs();
    }

    private void setupTabs() {

        tabLayout.addTab(tabLayout.newTab().setText("Conversations"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorites"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        tabsAdapter.setMessageList(messageList);
        tabsAdapter.notifyDataSetChanged();
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
