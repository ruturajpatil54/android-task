package com.task.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.internal.Excluder;
import com.task.R;
import com.task.callbacks.MessageCallback;
import com.task.err.ErrorLogger;
import com.task.models.MessageList;
import com.task.network.ApiService;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity implements MessageCallback {

    private static ProgressDialog progressDialog;
    private static ApiService.ApiServiceBinder apiServiceBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showMessage(getString(R.string.getting_messages),false);


        // connect to API service
        final Intent apiService = new Intent(this, ApiService.class);
        bindService(apiService, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                ErrorLogger.add(ErrorLogger.DEBUG,"api_service","Connected");
                apiServiceBinder = (ApiService.ApiServiceBinder)iBinder;

                // Fetch all messages
                apiServiceBinder.getMessages(HomeActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                ErrorLogger.add(ErrorLogger.DEBUG,"api_service","Disconnected");
            }
        }, Context.BIND_AUTO_CREATE);

    }



    public synchronized void showMessage(String dialogMessage,boolean allowCancel)
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
            ErrorLogger.add(ErrorLogger.WARNING,"show_message",e);
        }

    }
    public void closeDialog()
    {
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    @Override
    public void onMessagesRetreived(MessageList messageList) {
        closeDialog();
        ErrorLogger.add(ErrorLogger.DEBUG,"get_messages",messageList.toString());
    }
    @Override
    public void onGetMessageFailure(String status)
    {
        closeDialog();
        ErrorLogger.add(ErrorLogger.SEVERE,"get_messages",status);
    }
}
