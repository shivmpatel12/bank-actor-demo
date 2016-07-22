package com.ea.eadpm.skunkworks;

import com.ea.eadpm.skunkworks.dto.TradeDto;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;

/**
 * Created by Shiv on 7/15/2016.
 */
public interface Trade extends Actor
{
    Task<TradeDto> makeTrade(String tradeId, String username1, String username2, String requestForUser1, String dataForUser1,
                             String requestForUser2, String dataForUser2);

}
