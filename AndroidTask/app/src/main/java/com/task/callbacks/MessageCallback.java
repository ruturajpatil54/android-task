package com.task.callbacks;

import com.task.models.MessageList;

/**
 * Created by ruturaj on 13/2/17.
 */

public interface MessageCallback
{
    void onMessagesRetreived(MessageList messageList);
    void onGetMessageFailure(String status);
}
