package com.task.err;

import android.util.Log;


public class ErrorLogger  {
    // Error Level
    public static final int SEVERE = 0;
    public static final int DEBUG = 1;
    public static final int WARNING = 2;

    public static void add(int logLevel,String tag, Exception e)
    {
        // @TODO save error logs for severe level & send it to server for live environments.
        // For now just logging errors
        switch (logLevel)
        {
            case SEVERE: Log.e(tag," "+e.getLocalizedMessage());
                break;
            default: Log.d(tag," "+e.getLocalizedMessage());
        }
    }
    public static void add(int logLevel,String tag, String msg)
    {
        // @TODO save error logs for severe level & send it to server for live environments.
        // For now just logging errors
        switch (logLevel)
        {
            case SEVERE: Log.e(tag,msg);
                break;
            default: Log.d(tag,msg);

        }

    }

}
