package com.task.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.task.callbacks.MessageCallback;
import com.task.err.Logger;
import com.task.models.MessageList;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * API service makes all REST API calls & returns result through callbacks
 */
public class ApiService extends IntentService {
    // define endpoint for APIs
    final static String haptikTestServer = "http://haptik.mobi/android/";
    // currentURL holds the value of endpoint for use throughout the application.
    public static String currentUrl = haptikTestServer;
    private static Retrofit retrofit;

    public ApiService() {
        super("ApiService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ApiServiceBinder apiServiceBinder = new ApiServiceBinder();

        retrofit =
                new Retrofit.Builder().baseUrl(currentUrl).client(getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build();

        apiServiceBinder.delegate = retrofit.create(Delegate.class);

        return apiServiceBinder;
    }
    public OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient() {
            final Object TAG_CALL = new Object();

            @Override
            public okhttp3.Call newCall(Request request) {
                Request.Builder requestBuilder = request.newBuilder();
                requestBuilder.tag(TAG_CALL);
                return super.newCall(requestBuilder.build());
            }
        }.newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        OkHttpClient client = builder.dispatcher(new Dispatcher(Executors.newSingleThreadExecutor())).build();

        return client;
    }
    public class ApiServiceBinder extends Binder {
        public Delegate delegate;

        public void getMessages(final MessageCallback messageCallback) {
            try{
                Call<MessageList> getMessageListCall = delegate.getMessages();
                getMessageListCall.enqueue(new Callback<MessageList>() {
                    @Override
                    public void onResponse(Call<MessageList> call, retrofit2.Response<MessageList> response) {
                        if(response.isSuccessful())
                            messageCallback.onMessagesRetreived(response.body());
                        else
                            messageCallback.onGetMessageFailure(response.message());
                    }

                    @Override
                    public void onFailure(Call<MessageList> call, Throwable t) {
                        messageCallback.onGetMessageFailure(" " + t.getLocalizedMessage());
                    }
                });
            }
            catch (Exception e)
            {
                Logger.add(Logger.SEVERE,"get_messages",e);
            }
        }
    }
    public interface Delegate
    {
        @GET("test_data/")
        Call<MessageList> getMessages();
    }

}
