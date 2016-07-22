package com.ea.eadpm.skunkworks.handler;

import com.ea.eadpm.skunkworks.Trade;
import com.ea.eadpm.skunkworks.dto.AddTradeDto;
import com.ea.eadpm.skunkworks.dto.TradeDto;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.UUID;

/**
 * Created by Shiv on 7/15/2016.
 */
@Path("/trades")
public class TradeHandler
{

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Task<TradeDto> makeTrade(AddTradeDto tradeDto) {
        String tradeId = "trade" + UUID.randomUUID().toString();
        final Trade trade = Actor.getReference(Trade.class, tradeId);
        return trade.makeTrade(tradeId, tradeDto.getUsername1(), tradeDto.getUsername2(), tradeDto.getRequestTypeForUser1(),
                tradeDto.getDataForUser1(), tradeDto.getRequestTypeForUser2(), tradeDto.getDataForUser2());
    }
}
