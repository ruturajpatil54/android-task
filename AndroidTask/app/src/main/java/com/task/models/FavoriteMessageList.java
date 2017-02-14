package com.task.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.task.activities.HomeActivity.messageList;

/**
 * Created by ruturaj on 14/2/17.
 */
public class FavoriteMessageList extends MessageList
{
    public  HashMap<String,Integer> totalMessageCount = new HashMap<>();
    public  HashMap<String,HashSet<Message>> favoriteMessageMap = new HashMap<>();
    public FavoriteMessageList()
    {
        for(Message message:messageList.getMessages())
        {
            String userName = message.getUsername();
            if(totalMessageCount.containsKey(userName))
            {
                totalMessageCount.put(userName,totalMessageCount.get(userName)+1);
            }
            else
                totalMessageCount.put(userName,1);
        }
    }
    public void setFavorite(String userName,Message message)
    {
        if(favoriteMessageMap.containsKey(userName))
        {
            favoriteMessageMap.get(userName).add(message);
        }
        else
        {
            HashSet<Message> messageSet = new HashSet<>();
            messageSet.add(message);
            favoriteMessageMap.put(userName,messageSet);
        }
    }

    public void clearFavorite(Message currentMessage) {
        try{
            favoriteMessageMap.get(currentMessage.getUsername()).remove(currentMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public boolean isFavorite(Message message)
    {
        HashSet<Message> timeStamps = favoriteMessageMap.get(message.getUsername());
        return timeStamps!=null&&timeStamps.contains(message);
    }

}
