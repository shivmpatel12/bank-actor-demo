package com.ea.eadpm.skunkworks.dto;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Shiv on 7/15/2016.
 */
@Immutable
@XmlRootElement
public class TradeDto
{
    private String tradeId;
    private String username1;
    private String username2;
    private String requestTypeForUser1;
    private String dataForUser1;
    private String requestTypeForUser2;
    private String dataForUser2;

    public String getTradeId()
    {
        return tradeId;
    }

    public void setTradeId(final String tradeId)
    {
        this.tradeId = tradeId;
    }

    public String getUsername1()
    {
        return username1;
    }

    public void setUsername1(final String username1)
    {
        this.username1 = username1;
    }

    public String getUsername2()
    {
        return username2;
    }

    public void setUsername2(final String username2)
    {
        this.username2 = username2;
    }

    public String getRequestTypeForUser1()
    {
        return requestTypeForUser1;
    }

    public void setRequestTypeForUser1(final String requestTypeForUser1)
    {
        this.requestTypeForUser1 = requestTypeForUser1;
    }

    public String getDataForUser1()
    {
        return dataForUser1;
    }

    public void setDataForUser1(final String dataForUser1)
    {
        this.dataForUser1 = dataForUser1;
    }

    public String getRequestTypeForUser2()
    {
        return requestTypeForUser2;
    }

    public void setRequestTypeForUser2(final String requestTypeForUser2)
    {
        this.requestTypeForUser2 = requestTypeForUser2;
    }

    public String getDataForUser2()
    {
        return dataForUser2;
    }

    public void setDataForUser2(final String dataForUser2)
    {
        this.dataForUser2 = dataForUser2;
    }
}
