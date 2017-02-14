package com.task.utils;

/**
 * Created by ruturaj on 14/2/17.
 */

public enum MessageLink
{
    NONE("NONE"),
    FIRST("FIRST"),
    INTERMMEDIATE("INTERMMEDIATE"),
    LAST("LAST");
    private final String order;

    MessageLink(String order)
    {
        this.order = order;
    }
    public String value()
    {
        return order;
    }
}
