package com.task.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ruturaj on 13/2/17.
 */

public class MessageList {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("messages")
    @Expose
    private ArrayList<Message> messages;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
