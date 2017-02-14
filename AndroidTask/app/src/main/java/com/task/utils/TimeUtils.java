package com.task.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ruturaj on 14/2/17.
 */

public class TimeUtils
{
    public static Date parseDate(String messageTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// ex. 2016-01-01T05:36:34
        try{
            return dateFormat.parse(messageTime.replace("T"," "));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static String parseTimeString(String format,Date time)
    {

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(time);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
