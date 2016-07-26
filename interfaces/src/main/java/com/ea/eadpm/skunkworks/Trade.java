package com.ea.eadpm.skunkworks;

import cloud.orbit.actors.Actor;
import cloud.orbit.concurrent.Task;
import com.ea.eadpm.skunkworks.dto.UserDto;

import java.util.Map;

/**
 * Created by Shiv on 7/15/2016.
 */
public interface Trade extends Actor
{
    Task<Map<String, UserDto>> makeTrade(String tradeId, String username1, String username2, String requestForUser1, String dataForUser1,
                                         String requestForUser2, String dataForUser2);

}
