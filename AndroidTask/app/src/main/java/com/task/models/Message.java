package com.task.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ruturaj on 13/2/17.
 */

public class Message
{
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("image-url")
    @Expose
    private String imageUrl;
    @SerializedName("message-time")
    @Expose
    private String messageTime;

    transient String messageLinked; // indicates if message is part of consecutive messages by same user. Values indicated by Enum

    public String getMessageLinked() {
        return messageLinked;
    }

    public void setMessageLinked(String messageLinked) {
        this.messageLinked = messageLinked;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
