package com.ea.eadpm.skunkworks.dto;

/**
 * Created by Shiv on 7/15/2016.
 */
public class AddTradeDto
{
    private String username1;
    private String username2;
    private String requestTypeForUser1;
    private String dataForUser1;
    private String requestTypeForUser2;
    private String dataForUser2;

    public String getUsername1()
    {
        return username1;
    }

    public String getUsername2()
    {
        return username2;
    }

    public String getRequestTypeForUser1()
    {
        return requestTypeForUser1;
    }

    public String getDataForUser1()
    {
        return dataForUser1;
    }

    public String getRequestTypeForUser2()
    {
        return requestTypeForUser2;
    }

    public String getDataForUser2()
    {
        return dataForUser2;
    }
}
